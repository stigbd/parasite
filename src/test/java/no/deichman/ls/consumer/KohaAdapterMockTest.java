/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.deichman.ls.consumer;

import no.deichman.ls.adapter.KohaAdapterMock;
import java.util.HashMap;
import no.deichman.ls.domain.Manifestation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sbd
 */
public class KohaAdapterMockTest {
    
    public KohaAdapterMockTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getManifestationList method, of class KohaAdapterMock.
     */
    @Test
    public void testGetManifestationList() {
        System.out.println("getManifestationList");
        KohaAdapterMock instance = new KohaAdapterMock();
        HashMap<String, Manifestation> expResult = null;
        HashMap<String, Manifestation> result = instance.getManifestationList();
        assertNotNull(result);
        // assertEquals(expResult, result);
    }

    /**
     * Test of getManifestationsByWorkId method, of class KohaAdapterMock.
     */
    @Test
    public void testGetManifestationsByWorkId() {
        System.out.println("getManifestationsByWorkId");
        String id = "1";
        KohaAdapterMock instance = new KohaAdapterMock();
        HashMap<String, Manifestation> expResult = null;
        HashMap<String, Manifestation> result = instance.getManifestationsByWorkId(id);
        assertNotNull(result);
        // assertEquals(expResult, result);
    }

    /**
     * Test of getManifestationById method, of class KohaAdapterMock.
     */
    @Test
    public void testGetManifestationById() {
        System.out.println("getManifestationById");
        String manifestationId = "1";
        KohaAdapterMock instance = new KohaAdapterMock();
        Manifestation expResult = null;
        Manifestation result = instance.getManifestationById(manifestationId);
        assertNotNull(result);
        // assertEquals(expResult, result);
    }
}