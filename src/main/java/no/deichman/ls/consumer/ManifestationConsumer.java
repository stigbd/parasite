/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.deichman.ls.consumer;

import java.util.HashMap;
import no.deichman.ls.domain.Manifestation;

/**
 *
 * @author sbd
 */
public interface ManifestationConsumer {

    Manifestation getManifestationById(int manifestationId);

    HashMap<Integer, Manifestation> getManifestationList();

    HashMap<Integer, Manifestation> getManifestationsByWorkId(int id);
    
}