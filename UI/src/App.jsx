import { Elements } from '@stripe/react-stripe-js';
import { loadStripe } from '@stripe/stripe-js';
import CheckoutForm from './Components/CheckoutForm'; // Import your checkout form component

const stripePromise = loadStripe('pk_test_51Qg0w2Da5IPAiAcoyHLFdLYyOxDwKyzXuUNwTrCvFYlVr3SFNT5lnjFfTPsvgUhip4eNwGWjo0cNYS9y9u9xlnLK002EvhGu0d'); // Replace with your actual publishable key

function App() {
    return (
        <Elements stripe={stripePromise}>
            <CheckoutForm/>
        </Elements>
    );
}

export default App;
