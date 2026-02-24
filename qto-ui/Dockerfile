# Build stage - Bun for fast install and build
FROM oven/bun:1 AS builder
WORKDIR /app

COPY package.json bun.lock* ./
RUN bun install --frozen-lockfile

COPY . .
RUN bun run build:docker

# Run stage - nginx serves static files and proxies API to backend
FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html

# nginx config: serve /qto-ops, proxy /qto to backend
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
