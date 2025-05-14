import { consumer } from "../config/kafka.config";
import Notification from "../models/notification.model";
/**
 * Consumes messages from the 'product-events' Kafka topic.
 * Each message is stored as a notification with type 'PRODUCT'.
 */
export const consumeProductMessages = async (): Promise<void> => {
  try {
    // Connect to Kafka broker
    await consumer.connect();
    // Subscribe to the 'product-events' topic from the beginning
    await consumer.subscribe({ topic: "product-events", fromBeginning: true });
    // Start consuming messages
    await consumer.run({
      eachMessage: async ({ topic, partition, message }) => {
        try {
          const value = message.value?.toString();

          if (!value) {
            console.warn("Received empty message on topic:", topic);
            return;
          }

          const eventData = JSON.parse(value);
          console.log(`Received message from topic [${topic}], partition [${partition}]:`, eventData);
          // Save the event data in the notification collection
          await Notification.create({
            type: "PRODUCT",
            data: eventData,
          });
        } catch (err) {
          console.error(" Error processing message:", err);
        }
      },
    });
  } catch (err) {
    console.error("Failed to start Kafka consumer for product-events:", err);
    process.exit(1);
  }
};
