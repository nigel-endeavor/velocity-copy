import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  optimizeDeps: {
    include: ['@azure/msal-browser', '@azure/msal-react'],
    esbuildOptions: {
      target: 'esnext',
    },
  },
  server: {
    port: 4200,
    proxy: {
      '/qto/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      },
      '/qto/actuator': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
  base: '/qto-ops/',
  build: {
    outDir: 'dist',
    sourcemap: true,
  },
})
