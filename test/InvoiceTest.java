import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class InvoiceTest {

    Invoice invoice;

    @Before
    public void init () {
        invoice = new Invoice ();
    }

    @Test
    public void testInitialState () {
        System.out.println ("============ Test constructor (in initialized state)");
        System.out.println ("============ Test whether in initialized state");
        assertTrue (invoice.isInitialized());
        System.out.println ("============");
    }

    @Test
    public void testActionsFromInitialState () {

        System.out.println ("============ Test actions from Initial State (only start allowed)");

        // Invoice is in Initial State and can't be approved and payment can't be processed.
        invoice.approve();
        assertFalse (invoice.isSent ());
        invoice.processPayment();
        assertFalse (invoice.isPaid ());

        // Invoice is in Initial State and can be started into Concept State.
        System.out.println ("============ Only start allowed");
        invoice.start();
        assertTrue (invoice.isConcept());
        System.out.println ("============");
    }

    @Test
    public void testActionsFromConceptState () {

        System.out.println ("============ Test actions from Concept State (only approve allowed)");

        // First upgrade Invoice to Concept State.
        invoice.start();
        assertTrue (invoice.isConcept());

        // Invoice is in Concept State and can't be started and payment can't be processed.
        invoice.start ();
        assertTrue (invoice.isConcept ());
        invoice.processPayment ();
        assertFalse (invoice.isPaid());

        // Invoice is in Concept State and can be approved to Sent State.
        System.out.println ("============ Only approve allowed");
        invoice.approve();
        assertTrue (invoice.isSent ());

        System.out.println ("============");
    }

    @Test
    public void testActionsFromSentState () {

        System.out.println ("============ Test actions from Sent State (only process payment allowed)");

        // First upgrade Invoice to Sent State.
        invoice.start();
        invoice.approve ();
        assertTrue (invoice.isSent());

        // Invoice is in Sent State and can't be started or approved.
        invoice.start ();
        assertFalse (invoice.isConcept ());
        invoice.approve ();
        assertTrue (invoice.isSent());

        // Invoice is in Sent State and payment can be processed to Paid State.
        System.out.println ("============ Only process payment allowed");
        invoice.processPayment();
        assertTrue (invoice.isPaid ());

        System.out.println ("============");

    }

    @Test
    public void testActionsFromPaidState () {

        System.out.println ("============ Test actions from Paid State (no action allowed)");

        // First upgrade Invoice to Paid State.
        invoice.start();
        invoice.approve ();
        invoice.processPayment();
        assertTrue (invoice.isPaid());

        // Invoice is in Paid State and can't be upgraded to any other State.
        invoice.start ();
        assertFalse (invoice.isConcept ());
        invoice.approve ();
        assertFalse (invoice.isSent());
        invoice.processPayment();
        assertTrue (invoice.isPaid ());

        System.out.println ("============");
    }
}