# Use Node.js base image
FROM node:18

# Set working directory
WORKDIR /app

# Copy package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy source code
COPY . .

# Build TypeScript files
RUN npm run build

# Expose the application port
EXPOSE 3001

# Start the application
CMD ["npm", "start"]
