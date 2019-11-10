# The builder from node image
FROM node:alpine as builder

# build-time variables 
# prod|sandbox its value will be come from outside 
ARG env=prod

RUN apk update && apk add --no-cache make git

# Move our files into directory name "app"
WORKDIR /app
COPY package.json package-lock.json  /app/
RUN npm config set registry https://registry.npm.taobao.org
RUN npm install @angular/cli -g
RUN cd /app && npm install
COPY .  /app

# 设置共享卷
# RUN mkdir resources \
#	--mount source=airTicket-resources,target=resources

# Build with $env variable from outside
RUN cd /app && npm run build --prod

# Build a small nginx image with static website
FROM nginx:alpine
RUN rm -rf /usr/share/nginx/html/*
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=builder /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]