package com.plainvanilla.test.hibernate;

import com.plainvanilla.database.LogInterceptor;
import com.plainvanilla.test.utils.TestUtils;
import com.plainvanilla.vipbazaar.model.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Serializable;

import static com.plainvanilla.test.utils.TestUtils.*;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 06/09/13
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
 */

public class MappingTest {

    private static Configuration cfg;
    private static SessionFactory sf;
    private static Session s;
    private static Transaction tx;

    private static User buyer, seller;
    private static Item item;
    private static Category root;

    @BeforeClass
    public static void setUpConfiguration() {

        cfg = TestUtils.getConfiguration();
        cfg.setInterceptor(new LogInterceptor());
        sf = cfg.buildSessionFactory();

    }

    @Before
    public void setUpSession() {
        s = sf.openSession();
        tx = s.beginTransaction();

        // session will be flushed automagically during commit(),
        // before every query execution and when flush() is called explicitly
        //(default behavior)
        s.setFlushMode(FlushMode.AUTO);

    }

    @After
    public void commit() {
        if (s != null && tx != null) {
            tx.commit();
            s.close();
        }
    }

    @org.junit.Test
    public void testCreateEntities() {

        buyer = initPojo(User.class);
        seller = initPojo(User.class);
        item = initPojo(Item.class);
        root = initPojo(Category.class);

        Address address = initPojo(Address.class);
        Address address2 = initPojo(Address.class);
        BillingDetails buyerCard = initPojo(CreditCard.class);
        BillingDetails sellerAccount = initPojo(BankAccount.class);
        Bid bid1 = initPojo(Bid.class);
        Shipment shipment = initPojo(Shipment.class);
        Bid bid2 = initPojo(Bid.class);
        Comment comment = initPojo(Comment.class);
        Category sub = initPojo(Category.class);

        bid1.setAmount(100);
        bid2.setAmount(101);

        shipment.setBuyer(buyer);
        shipment.setSeller(seller);
        shipment.setDelivery(buyer.getHome());

        root.addCategory(sub);
        item.addCategory(root);
        item.addCategory(sub);

        item.addComment(comment, buyer);
        item.addComment("Another Comment", seller);
        buyer.addComment("Yet another comment", item);

        buyer.addBoughtItem(item);
        buyer.setHome(address);
        buyer.addBillingDetails(buyerCard);

        seller.addSoldItem(item);
        seller.setHome(address2);
        seller.addBillingDetails(sellerAccount);

        item.setShipment(shipment);
        item.addBid(bid1);
        item.addBid(bid2);
        buyer.addBid(bid1);
        buyer.addBid(bid2);

        s.save(buyer);
        s.save(seller);
        s.save(root);

        assertNotNull(item.getId());
        assertNotNull(buyer.getId());

        // test cascading
        assertNotNull(buyerCard.getId());
        assertNotNull(sellerAccount.getId());
        assertNotNull(seller.getId());
        assertNotNull(bid1.getId());
        assertNotNull(bid2.getId());
        assertNotNull(item.getSuccessfulBid());
        assertNotNull(shipment.getId());
        assertNotNull(comment.getId());
        assertNotNull(root.getId());
        assertNotNull(sub.getId());

    }


    @org.junit.Test
    public void testGetEntities() {

       User seller2 = (User)s.get(User.class, (Serializable) seller.getId());
       User buyer2 = (User)s.get(User.class, (Serializable) buyer.getId());
       Item item2 = (Item)s.get(Item.class, (Serializable) item.getId());

       // test if entities were found
       assertNotNull(seller2);
       assertNotNull(buyer2);
       assertNotNull(item2);

       // verify equality
       assertTrue(seller2.equals(seller));
       assertTrue(buyer2.equals(buyer));
       assertTrue(item2.equals(item));

       // Hibernate uses persistent context scoped identity
       assertFalse(seller2 == seller);
       assertFalse(buyer2 == buyer);
       assertFalse(item2 == item);

       BillingDetails sellerBD = seller2.getDefaultBillingDetails();
       BillingDetails buyerBD = buyer2.getDefaultBillingDetails();

       assertNotNull(sellerBD);
       assertTrue(sellerBD.equals(seller.getDefaultBillingDetails()));
       assertNotNull(buyerBD);
       assertTrue(buyerBD.equals(buyer.getDefaultBillingDetails()));
       assertNotNull(item2.getItemBids());
       assertTrue(buyer2.getBids().size() == buyer.getBids().size());
       assertNotNull(item2.getSuccessfulBid());
       assertTrue(item.getSuccessfulBid().equals(item2.getSuccessfulBid()));

       assertNotNull(item2.getShipment());
       assertTrue(buyer2.getReceivedShipments().size() == 1);
       assertTrue(seller2.getSentShipments().size() == 1);

       assertTrue(item2.getComments().size() == 3);
       assertTrue(buyer2.getComments().size() == 2);
       assertTrue(seller2.getComments().size() == 1);
       assertTrue(item2.getCategories().size() == 2);
    }

    @Test(expected = org.hibernate.ObjectNotFoundException.class)
    public void testLoadNonExistentEntities() {
        try {
            User u = (User)s.load(User.class, (Serializable) 4567321L);
            u.getBids();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @org.junit.Test
    public void testLoadEntities() {

        User seller2 = null;
        User buyer2 = null;
        Item item2 = null;

        seller2 = (User)s.load(User.class, (Serializable) seller.getId());
        buyer2 = (User)s.load(User.class, (Serializable) buyer.getId());
        item2 = (Item)s.load(Item.class, (Serializable) item.getId());

        assertFalse(Hibernate.isInitialized(item2));
        assertFalse(Hibernate.isInitialized(seller2));
        assertFalse(Hibernate.isInitialized(buyer2));

        assertTrue(seller2.equals(seller));
        assertTrue(buyer2.equals(buyer));
        assertTrue(item2.equals(item));

        BillingDetails sellerBD = seller2.getDefaultBillingDetails();
        BillingDetails buyerBD = buyer2.getDefaultBillingDetails();

        assertNotNull(sellerBD);
        assert(sellerBD.equals(seller.getDefaultBillingDetails()));
        assertNotNull(buyerBD);
        assert(buyerBD.equals(buyer.getDefaultBillingDetails()));
        assertNotNull(item2.getItemBids());
        assertTrue(buyer2.getBids().size() == buyer.getBids().size());
        assertNotNull(item2.getSuccessfulBid());
        assertTrue(item2.getSuccessfulBid().equals(item.getSuccessfulBid()));

        assertNotNull(item2.getShipment());
        assertTrue(buyer2.getReceivedShipments().size() == 1);
        assertTrue(seller2.getSentShipments().size() == 1);

        assertTrue(item2.getComments().size() == 3);
        assertTrue(buyer2.getComments().size() == 2);
        assertTrue(seller2.getComments().size() == 1);
        assertTrue(item2.getCategories().size() == 2);
    }


    @org.junit.Test
    public void testUpdateEntities() {

        // load entities from to current persistent context
        User buyerFromDB = (User)s.get(User.class, buyer.getId());
        User sellerFromDB = (User)s.get(User.class, seller.getId());
        Item itemFromDB = (Item)s.get(Item.class, item.getId());

        buyerFromDB.setFirstName("Alex");
        sellerFromDB.setFirstName("Petr");
        itemFromDB.setName("The Item");

        // flush and commit the changes, start new persistent context
        commit();
        setUpSession();

        // load the entities from DB to the new persistent context
        buyerFromDB = (User)s.get(User.class, buyer.getId());
        sellerFromDB = (User)s.get(User.class, seller.getId());
        itemFromDB = (Item)s.get(Item.class, item.getId());

        // test the changes
        assertTrue(buyerFromDB.getFirstName().equals("Alex"));
        assertTrue(sellerFromDB.getFirstName().equals("Petr"));
        assertTrue(itemFromDB.getName().equals("The Item"));

    }


    @Test(expected = org.hibernate.StaleObjectStateException.class)
    public void testMergeEntity() {

        // update detached entities from previous session
        buyer.setFirstName("Martin");
        seller.setFirstName("Karel");
        item.setName("Some item");

        // reattach detached entities by merging them to current session
        Item mergedItem  = (Item)s.merge(item);
        User mergedBuyer = (User)s.merge(buyer);
        User mergedSeller = (User)s.merge(seller);

    }

    @Test
    public void testReattachEntities() {

        int sellerVersion = seller.getVersion().intValue();
        int buyerVersion = buyer.getVersion().intValue();
        int itemVersion = item.getVersion().intValue();

        item.setName("ReattachedName");
        seller.setFirstName("ReattachedSellerName");
        buyer.setFirstName("ReattachedBuyerName");

        // re-attach entities from previous session
        // they will be treated as dirty -> SQL UPDATE is made during session Flush
        s.refresh(item);
        s.refresh(seller);
        s.refresh(buyer);

        assertFalse(sellerVersion == seller.getVersion());
        assertFalse(buyerVersion == buyer.getVersion());
        assertFalse(itemVersion == item.getVersion());
        // entities are now reattached -> even both changes made before and after re-attachment are persisted
        item.setDescription("ReattachedDescription");
        seller.setLastName("ReattachedSellerLastName");
        buyer.setLastName("ReattachedBuyerLastName");

        commit();
        setUpSession();

        Item itemFromDB = (Item)s.get(Item.class, item.getId());
        User sellerFromDB = (User)s.get(User.class, seller.getId());
        User buyerFromDB = (User)s.get(User.class, buyer.getId());
                                 /*
        assertTrue(item.getName().equals(itemFromDB.getName()));
        assertTrue(item.getDescription().equals(itemFromDB.getDescription()));
        assertTrue(seller.getFirstName().equals(sellerFromDB.getFirstName()));
        assertTrue(seller.getLastName().equals(sellerFromDB.getLastName()));
        assertTrue(buyer.getFirstName().equals(buyerFromDB.getFirstName()));
        assertTrue(buyer.getLastName().equals(buyerFromDB.getLastName())); */
    }

    @Test(expected = org.hibernate.NonUniqueObjectException.class)
    public void testReattachDuplicateEntity() {

        Item itemFromDB2 = (Item)s.get(Item.class, item.getId());

        // NOT Ok - now we would have two instances representing the same DB row in persistent context
        // -> exception
        s.update(item);
    }


    @Test
    public void testLockEntities() {

        item.setName("Before Lock Name");

        Session.LockRequest request = s.buildLockRequest(LockOptions.NONE);
        request.lock(item);

        item.setDescription("After Lock");

        commit();
        setUpSession();

        Item itemFromDB = (Item)s.get(Item.class, item.getId());

        assertTrue(itemFromDB.getDescription().equals(item.getDescription()));
        assertFalse(itemFromDB.getName().equals(item.getName()));


    }


    @org.junit.Test
    public void testRemoveEntities() {

        // delete does BOTH re-attachment and deletion
        // -> we do not need to load or re-attach detached objects manually
        s.delete(seller);
        s.delete(buyer);
        s.delete(item);

        // commit the changes and start new persistent context
        commit();
        setUpSession();

        // test that entities were deleted
        User sellerFromDB = (User)s.get(User.class, seller.getId());
        User buyerFromDB = (User)s.get(User.class, buyer.getId());
        Item itemFromDb = (Item)s.get(Item.class, item.getId());

        assertNull(sellerFromDB);
        assertNull(buyerFromDB);
        assertNull(itemFromDb);

        // check that tables are empty
        assertTrue(s.createQuery("from User").list().isEmpty());
        assertTrue(s.createQuery("from Item").list().isEmpty());
        assertTrue(s.createQuery("from BillingDetails").list().isEmpty());
        assertTrue(s.createQuery("from CreditCard").list().isEmpty());
        assertTrue(s.createQuery("from BankAccount").list().isEmpty());
        assertTrue(s.createQuery("from Bid").list().isEmpty());
        assertTrue(s.createQuery("from Comment").list().isEmpty());
        assertTrue(s.createQuery("from Shipment").list().isEmpty());

        // cascading from user did not reach category
        assertTrue(s.createQuery("from Category").list().size() == 2);

    }
}
