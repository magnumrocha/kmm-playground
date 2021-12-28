//
//  ConnectionStateView.swift
//  KMMPlayground
//
//  Created by Magnum Rocha on 28/11/2021.
//

import SwiftUI
import Shared

struct ConnectionStateView: View {
    @State var connection: ConnectionType? = nil

    private let shared = SharedWrapper()

    var body: some View {
        ZStack {
            Color(hex: 0x4f5b66)

            VStack {
                Image(connection.icon)
                    .resizable()
                    .scaledToFit()
                    .foregroundColor(.white)
                    .frame(width: 150.0, height: 150.0)
                    .padding()

                Text(connection.description)
                    .foregroundColor(.white)
                    .font(.title2)
                    .bold()
                    .padding([.bottom, .horizontal])
            }
        }
        .onAppear { onViewAppear() }
    }

    private func onViewAppear() {
        shared.networkConnectionObservation() { [self] networkConnection in
            self.connection = networkConnection
        }
    }
}

extension Color {
    init(hex: UInt, alpha: Double = 1) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xff) / 255,
            green: Double((hex >> 08) & 0xff) / 255,
            blue: Double((hex >> 00) & 0xff) / 255,
            opacity: alpha
        )
    }
}

extension Optional where Wrapped == ConnectionType {
    var icon: String {
        get {
            guard let unwrapped = self else {
                return "NoConnection"
            }
            switch unwrapped {
                case .wifi: return "WifiConnection"
                case .mobile: return "MobileConnection"
                default: return "UnKnown"
            }
        }
    }

    var description: String {
        get {
            guard let unwrapped = self else {
                return "No Connection"
            }
            switch unwrapped {
                case .wifi: return "Connected By Wify"
                case .mobile: return "Connected By Mobile Network"
                default: return "UnKnown"
            }
        }
    }
}

struct ConnectionStateView_Previews: PreviewProvider {
    static var previews: some View {
        ConnectionStateView()
    }
}
