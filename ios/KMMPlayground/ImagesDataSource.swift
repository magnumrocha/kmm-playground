//
//  ImagesDataSource.swift
//  KMMPlayground
//
//  Created by Magnum Rocha on 29/11/2021.
//  Copyright © 2021 Magnum Rocha. All rights reserved.
//

import Foundation
import Shared

class ImagesDataSource: ObservableObject {

    @Published var items = [PicsumImage]()
    @Published var isLoadingPage = false
    @Published var isRequestFailed = false
    
    private var currentPage = 1
    private var canLoadMorePages = true

    private let shared = SharedWrapper()
    private let pageSize = 10
    
    func loadImages() async {
        await loadMoreImages()
    }

    func loadMoreImagesIfNeeded(currentItem item: PicsumImage?) async {
        guard let item = item else {
            await loadMoreImages()
            return
        }

        let thresholdIndex = items.index(items.endIndex, offsetBy: -5)
        if items.firstIndex(where: { $0.id == item.id }) == thresholdIndex {
            await loadMoreImages()
        }
    }

    private func loadMoreImages() async {
        guard !isLoadingPage && canLoadMorePages else {
            return
        }

        isLoadingPage = true

        do {
            let images = try await getImagesAsync(page: currentPage)
            DispatchQueue.main.async {
                self.items += images
                self.currentPage += 1
                self.isLoadingPage = false
                self.isRequestFailed = false
            }
        } catch {
            self.isRequestFailed = true
        }
    }

    private func getImagesAsync(page: Int) async throws -> [PicsumImage] {
        return try await withCheckedThrowingContinuation { continuation in
            shared.getImages(page: Int32(page), size: Int32(pageSize)) { data, error in
                if let images = data {
                    continuation.resume(returning: images)
                }
                if let errorReal = error {
                    continuation.resume(throwing: errorReal.asError())
                }
            }
        }
    }
}

extension PicsumImage : Identifiable {
    
}
