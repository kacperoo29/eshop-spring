FROM node:18-bullseye

WORKDIR /app

COPY ./eshop/package.json .
RUN npm install --force -g yarn
RUN yarn install

COPY ./eshop/* .

ENTRYPOINT [ "npm", "start" ]
