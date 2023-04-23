# Stage 1: Build the Angular app
FROM node:14 AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .

ARG RAVE_API_KEY
ARG BACKEND_URL


RUN echo "export const environment = { production: false, RAVE_API_KEY: '${RAVE_API_KEY}', BACKEND_URL: '${BACKEND_URL}' };" > src/environments/environment.ts
RUN echo "export const environment = { production: true, RAVE_API_KEY: '${RAVE_API_KEY}', BACKEND_URL: '${BACKEND_URL}' };" > src/environments/environment.prod.ts
RUN npm run build:prod

# Stage 2: Set up the Apache container to serve the built Angular app
FROM httpd:2.4.56-alpine
COPY --from=build /app/dist/freshfood /usr/local/apache2/htdocs/
