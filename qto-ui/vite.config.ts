import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path'

// https://vite.dev/config/
export default defineConfig(({ mode }) => ({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  optimizeDeps: {
    esbuildOptions: {
      target: 'esnext',
    },
  },
  server: {
    port: 4202,
    proxy: {
      '/qto/api': {
        target: 'http://localhost:8085',
        changeOrigin: true,
        secure: false,
      },
      '/qto/actuator': {
        target: 'http://localhost:8085',
        changeOrigin: true,
      },
    },
  },
  base: mode === 'production' ? '/qto-ops/' : '/',
  build: {
    outDir: 'dist',
    sourcemap: true,
  },
}))
