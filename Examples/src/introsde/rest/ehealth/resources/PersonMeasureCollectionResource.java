package introsde.rest.ehealth.resources;
import introsde.rest.ehealth.model.Person;
import introsde.rest.ehealth.model.LifeStatus;
import introsde.rest.ehealth.model.MeasureDefinition;


import java.io.IOException;
import java.util.List;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
public class PersonMeasureCollectionResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;

    String measureDescription;

    // will work only inside a Java EE application
    @PersistenceUnit(unitName="introsde-jpa")
    EntityManager entityManager;

    // will work only inside a Java EE application
    @PersistenceContext(unitName = "introsde-jpa",type=PersistenceContextType.TRANSACTION)
    private EntityManagerFactory entityManagerFactory;


     public PersonMeasureCollectionResource(UriInfo uriInfo, Request request,int id, EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.entityManager = em;
    }

    public PersonMeasureCollectionResource(UriInfo uriInfo, Request request,int id, String measureDescription) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.measureDescription = measureDescription;
    }

    // Return the list of people to the user in the browser
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<LifeStatus> getPersonHealthMeasureHistory() {        
        System.out.println("Getting list of Measures for Person... "+id);

        MeasureDefinition md = MeasureDefinition.getMeasureDefinitionByTitle(measureDescription);
        //System.out.println("CHARLES LINDO\n\n\n\n\n "+md.getMeasureName()+" \n\n\n\n\n ");
        List<LifeStatus> ls = Person.getLifeStatusHistory(id, md.getIdMeasureDef());
        return ls;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    public LifeStatus newLifeStatus(LifeStatus ls) throws IOException {
        System.out.println("Creating new Life Status Here we go... Value "+ls.getValue());  
        MeasureDefinition md = MeasureDefinition.getMeasureDefinitionByTitle(measureDescription);
        Person p = Person.getPersonById(id);
        ls.setMeasureDefinition(md);

        ls.setPerson(p);
        return LifeStatus.saveLifeStatus(ls);
    }




    // retuns the number of people
    // to get the total number of records
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        System.out.println("Getting count...");
        List<Person> people = Person.getAll();
        int count = people.size();
        return String.valueOf(count);
    }

    public Person getPersonById(int personId) {
        System.out.println("Reading person from DB with id: "+personId);

        // this will work within a Java EE container, where not DAO will be needed
        //Person person = entityManager.find(Person.class, personId); 

        Person person = Person.getPersonById(personId);
        System.out.println("Person: "+person.toString());
        return person;
    }

    
    @Path("{mid}")
    public PersonMeasureResource getPerson(@PathParam("mid") int mid) {
        return  new PersonMeasureResource(uriInfo, request, id, mid);
    }

}