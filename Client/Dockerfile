FROM node:18-bullseye

WORKDIR /app

COPY ./eshop/* .

RUN npm install --force -g yarn
RUN yarn install

ENTRYPOINT [ "npm", "start" ]
