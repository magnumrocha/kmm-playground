//
//  ImageListView.swift
//  KMMPlayground
//
//  Created by Magnum Rocha on 28/11/2021.
//

import SwiftUI
import Shared

struct ImageListView: View {
    @StateObject var dataSource = ImagesDataSource()

    var body: some View {
        ScrollView {
            LazyVStack {
                ForEach(dataSource.items) { item in
                    ImageItemView(image: item)
                        .task {
                            await dataSource.loadMoreImagesIfNeeded(currentItem: item)
                        }
                        .padding([.leading, .trailing, .top], 20)
                }

                if dataSource.isLoadingPage {
                    ProgressView()
                }
            }
        }
        .task {
            await dataSource.loadImages()
        }
    }
}

struct ImageItemView: View {
    var image: PicsumImage
    
    init(image: PicsumImage) {
        self.image = image
    }
    
    var body: some View {
        AsyncImage(url: URL(string: image.getImageWithSize(width: 400, height: 300)),
                   transaction: .init(animation: .easeInOut)
        ) { phase in
            switch phase {
                case .success(let loadedImage):
                    loadedImage
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                        .clipShape(RoundedRectangle(cornerRadius: 15))
                        .padding(.all, 16)

                case .failure(let error):
                    ErrorView(error: error)

                case .empty:
                    WaitingView()

                @unknown default:
                    EmptyView()
            }
        }
        .frame(width: UIScreen.main.bounds.width, height: 250)
        .overlay(
            ZStack {
                Text(image.author)
                    .font(.callout)
                    .padding(6)
                    .foregroundColor(.white)
            }.background(Color.black)
            .opacity(0.7)
            .cornerRadius(10.0)
            .padding([.leading, .trailing], 22),
            alignment: .bottomLeading
        )
    }
}

struct ErrorView: View {
    var error: Error

    init(error: Error) {
        self.error = error
    }
    
    var body: some View {
        Text(error.localizedDescription)
    }
}

struct WaitingView: View {
    var body: some View {
        VStack {
            ProgressView()
                .progressViewStyle(CircularProgressViewStyle(tint: .indigo))

            Text("Fetching image...")
        }
    }
}
