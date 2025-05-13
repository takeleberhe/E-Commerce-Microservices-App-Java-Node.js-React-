import app from "./app";
import { connectDB } from "./config/mongodb.config";
import { consumeProductMessages } from "./consumers/product.consumer";
//import { consumeOrderMessages } from "./consumers/order.consumer";
//import { consumePaymentMessages } from "./consumers/payment.consumer";

const PORT = process.env.PORT || 4000;

const start = async () => {
  await connectDB();
  await consumeProductMessages();
  //await consumeOrderMessages();
  //await consumePaymentMessages();

  app.listen(PORT, () => {
    console.log(`🚀 Notification service running on port ${PORT}`);
  });
};

start();
