package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.MeasureDefinition;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class MeasureResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public MeasureResource(UriInfo uriInfo, Request request,int id, EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.entityManager = em;
    }

    public MeasureResource(UriInfo uriInfo, Request request,int id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    // Application integration
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MeasureDefinition getMeasureDefinition() {
        MeasureDefinition md = this.getMeasureDefinitionById(id);
        if (md == null)
            throw new RuntimeException("Get: MeasureDefinition with " + id + " not found");
        return md;
    }

    // for the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public MeasureDefinition getMeasureDefinitionHTML() {
        MeasureDefinition md = this.getMeasureDefinitionById(id);
        if (md == null)
            throw new RuntimeException("Get: MeasureDefinition with " + id + " not found");
        System.out.println("Returning MeasureDefinition... " + md.getIdMeasureDef());
        return md;
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putMeasureDefinition(MeasureDefinition md) {
        System.out.println("--> Updating MeasureDefinition... " +this.id);
        System.out.println("--> "+md.toString());
        MeasureDefinition.updateMeasureDefinition(md);
        Response res;
        MeasureDefinition existing = getMeasureDefinitionById(this.id);

        if (existing == null) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
            md.setIdMeasureDef(this.id);
            MeasureDefinition.updateMeasureDefinition(md);
        }
        return res;
    }

    @DELETE
    public void deleteMesureDefinition() {
        MeasureDefinition md = getMeasureDefinitionById(id);
        if (md == null)
            throw new RuntimeException("Delete: MeasureDefinition with " + id
                    + " not found");
        MeasureDefinition.removeMeasureDefinition(md);
    }

    public MeasureDefinition getMeasureDefinitionById(int id) {
        System.out.println("Reading person from DB with id: "+id);

        // this will work within a Java EE container, where not DAO will be needed
        //Person person = entityManager.find(Person.class, personId); 

        MeasureDefinition md = MeasureDefinition.getMeasureDefinitionById(id);
        System.out.println("getMeasureDefinition: "+md.toString());
        return md;
    }
}