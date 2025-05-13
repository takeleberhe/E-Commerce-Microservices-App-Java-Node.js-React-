import { consumer } from "../config/kafka.config";
import Notification from "../models/notification.model";

export const consumeProductMessages = async () => {
  await consumer.connect();
  await consumer.subscribe({ topic: "product-events", fromBeginning: true });

  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      const data = JSON.parse(message.value!.toString());
      console.log("📦 Received product event:", data);

      await Notification.create({
        type: "PRODUCT",
        data,
      });
    },
  });
};
