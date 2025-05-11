import { useState } from "react";
import { CardElement, useStripe, useElements } from "@stripe/react-stripe-js";
import axios from "axios";

const CheckoutForm = () => {
  const stripe = useStripe();
  const elements = useElements();
  const [paymentStatus, setPaymentStatus] = useState("");
  const [amount, setAmount] = useState(5000); // Default to $50 (in cents)
  const [currency, setCurrency] = useState("usd");
  const [description, setDescription] = useState("Test payment");
  const [gateway, setGateway] = useState("stripe"); // Default to Stripe
  const [loading, setLoading] = useState(false); // Loading state for payment submission

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!stripe || !elements) {
      return;
    }

    setLoading(true); // Show loading state when payment is being processed
    setPaymentStatus(""); // Reset the payment status

    try {
      let clientSecret;

      if (gateway === "stripe") {
        // Create PaymentIntent on the backend (Stripe)
        const { data } = await axios.post("http://localhost:8081/api/payments", {
          amount,
          currency,
          description,
          gateway
        });
        clientSecret = data.clientSecret; // Assuming backend sends clientSecret
        console.log(clientSecret);
      } else if (gateway === "paypal") {
        // For PayPal, create an order on your backend
        const { data } = await axios.post(
          "http://localhost:8081/api/payment/create-paypal-order",
          {
            amount,
            currency,
            description,
          }
        );
        const orderId = data.orderId; // Assuming the backend returns the order ID

        // Redirect to PayPal for approval
        window.location.href = `https://www.paypal.com/checkoutnow?token=${orderId}`;
        return; // Prevent further execution if redirecting to PayPal
      }

      // Step 2: Confirm the payment with the client secret (for Stripe)
      if (gateway === "stripe") {
        const cardElement = elements.getElement(CardElement);
        const { error, paymentIntent } = await stripe.confirmCardPayment(clientSecret, {
          payment_method: {
            card: cardElement,
            billing_details: {
              name: "Test User", // You can dynamically set this if needed
            },
          },
        });

        if (error) {
          setPaymentStatus(`Payment failed: ${error.message}`);
        } else if (paymentIntent.status === "succeeded") {
          setPaymentStatus("Payment successful!");
        }
      }
    } catch (error) {
      setPaymentStatus(`Error: ${error.message}`);
    } finally {
      setLoading(false); // Hide loading state after the payment process is finished
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-r from-blue-500 to-indigo-600 px-4 sm:px-6 lg:px-8">
      <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md sm:max-w-lg lg:max-w-xl xl:max-w-2xl">
        <h2 className="text-3xl font-semibold text-gray-800 mb-6 text-center">
          Payment Checkout
        </h2>

        {/* Gateway Selection Dropdown */}
        <div className="mb-6">
          <label htmlFor="gateway" className="block text-sm font-medium text-gray-700">
            Select Payment Gateway
          </label>
          <select
            id="gateway"
            value={gateway}
            onChange={(e) => setGateway(e.target.value)}
            className="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="stripe">Stripe</option>
            <option value="paypal">PayPal</option>
          </select>
        </div>

        <form onSubmit={handleSubmit} className="space-y-6">
          {/* Amount Input */}
          <div className="mb-6">
            <label htmlFor="amount" className="block text-sm font-medium text-gray-700">
              Amount (in dollars)
            </label>
            <input
              id="amount"
              type="number"
              value={amount / 100} // Show in dollars (e.g., 50.00 instead of 5000)
              onChange={(e) => setAmount(e.target.value * 100)} // Convert to cents
              className="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* Currency Input */}
          <div className="mb-6">
            <label htmlFor="currency" className="block text-sm font-medium text-gray-700">
              Currency (e.g., USD, EUR)
            </label>
            <input
              id="currency"
              type="text"
              value={currency}
              onChange={(e) => setCurrency(e.target.value)}
              className="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* Description Input */}
          <div className="mb-6">
            <label htmlFor="description" className="block text-sm font-medium text-gray-700">
              Description
            </label>
            <input
              id="description"
              type="text"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* Card Element (Only visible if Stripe is selected) */}
          {gateway === "stripe" && (
            <div className="border border-gray-300 rounded-lg p-4 mb-6 bg-gray-50">
              <CardElement
                options={{
                  style: {
                    base: {
                      fontSize: "16px",
                      color: "#424770",
                      "::placeholder": {
                        color: "#aab7c4",
                      },
                    },
                    invalid: {
                      color: "#9e2146",
                    },
                  },
                }}
              />
            </div>
          )}

          {/* Submit Button */}
          <button
            type="submit"
            disabled={!stripe || loading}
            className="w-full bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700 transition duration-200 disabled:bg-gray-400 disabled:cursor-not-allowed"
          >
            {loading ? "Processing..." : "Pay"}
          </button>
        </form>

        {/* Payment Status */}
        {paymentStatus && (
          <p
            className={`mt-6 text-center text-sm font-medium ${
              paymentStatus.includes("successful")
                ? "text-green-600"
                : "text-red-600"
            }`}
          >
            {paymentStatus}
          </p>
        )}
      </div>
    </div>
  );
};

export default CheckoutForm;
