import {fileURLToPath, URL} from 'node:url'

import {defineConfig, loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import basicSsl from '@vitejs/plugin-basic-ssl'

export default defineConfig(({command, mode}) => {
  const env = loadEnv(mode, process.cwd(), '')

  return {
    plugins: [
      vue(),
      vueDevTools(),
      basicSsl()
    ],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url))
      },
    },
    server: {
      https: true,
      port: parseInt(env.VITE_PORT),
      proxy: {
        "/api": {
          target: `https://${env.VITE_BACK_END_IP}:${env.VITE_PORT_BACK_END}`,
          changeOrigin: true,
          secure: false
        }
      }
    }
  }
})