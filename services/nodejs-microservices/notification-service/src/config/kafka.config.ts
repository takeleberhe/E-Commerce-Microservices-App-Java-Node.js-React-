import { Kafka } from "kafkajs";
import dotenv from "dotenv";
dotenv.config();

export const kafka = new Kafka({
  clientId: "notification-service",
  brokers: [process.env.KAFKA_BROKER!],
});

export const consumer = kafka.consumer({ groupId: process.env.KAFKA_GROUP_ID! });
