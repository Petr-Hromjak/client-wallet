import bundleAnalyzer from '@next/bundle-analyzer';

/** Configure the Next.js bundle analyzer */
const withBundleAnalyzer = bundleAnalyzer({
  enabled: process.env.ANALYZE === 'true',
});

/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: false,
  eslint: {
    ignoreDuringBuilds: true,
  },
  async redirects() {
    return [
      {
        source: "/",
        destination: "/wallets",
        permanent: true, // Set to `false` if this is temporary
      },
    ];
  },
};

export default withBundleAnalyzer(nextConfig);