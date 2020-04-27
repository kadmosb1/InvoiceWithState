public class Invoice {

    private static InvoiceState initialState;
    private static InvoiceState conceptState;
    private static InvoiceState sentState;
    private static InvoiceState paidState;

    private InvoiceState state;

    public Invoice () {
        initialState = new InvoiceInitialState (this, "Initial");
        conceptState = new InvoiceConceptState (this, "Concept");
        sentState = new InvoiceSentState(this, "Sent");
        paidState = new InvoicePaidState(this, "Paid");
        state = initialState;
    }

    public boolean isInitialized () {
        return state == initialState;
    }

    public void start() {
        state.start ();
    }

    public boolean isConcept () {
        return state == conceptState;
    }

    public void approve() {
        state.approve ();
    }

    public boolean isSent () {
        return state == sentState;
    }

    public void processPayment() {
        state.processPayment();
    }

    public boolean isPaid () {
        return state == paidState;
    }

    protected void newInvoice () {
        state = conceptState;
        System.out.println ("A new invoice has been drawn up.");
    }

    protected void approveInvoice () {
        state = sentState;
        System.out.println ("The invoice has been approved");
    }

    protected void processPaymentForInvoice () {
        state = paidState;
        System.out.println ("The customer has paid the invoice.");
    }

    protected void log (String state, String action) {
        System.out.format ("LOG: It isn't allowed to change states from %s using action %s%n", state, action);
    }
}
