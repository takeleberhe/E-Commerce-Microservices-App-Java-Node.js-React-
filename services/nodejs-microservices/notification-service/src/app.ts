
import express, { Application } from "express";
import dotenv from "dotenv";
import mongoose from "mongoose";
import morgan from "morgan";

import notificationRoutes from "./routes/notification.routes"; // placeholder route file

dotenv.config();

const app: Application = express();

// Middleware
app.use(express.json());
app.use(morgan("dev")); // logs HTTP requests

// Routes
app.use("/api/notifications", notificationRoutes);

// Health check
app.get("/", (_req, res) => {
  res.send("🚀 Notification Service is up and running");
});

export default app;
