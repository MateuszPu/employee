package net.guides.springboot2.springboot2jpacrudexample.employee


import com.mongodb.MongoClient
import com.mongodb.ServerAddress
import de.bwaldvogel.mongo.MongoServer
import de.bwaldvogel.mongo.backend.memory.MemoryBackend
import net.guides.springboot2.springboot2jpacrudexample.employee.request.CreateEmployee
import net.guides.springboot2.springboot2jpacrudexample.employee.view.EmployeeView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoDbFactory
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import spock.lang.Specification

@SpringBootTest(classes = EmployeeServiceIntegrationTest.TestConfiguration.class)
class EmployeeServiceIntegrationTest extends Specification {

    @Autowired
    private EmployeeRepository employeeRepository

    private EmployeeService underTest

    def setup() {
        underTest = new EmployeeServiceImpl(employeeRepository)
        employeeRepository.deleteAll()
    }

    def 'should create employee when correct request received'() {
        given:
        def request = new CreateEmployee("firstName", "lastName", "email", "12345")

        when:
        String id = underTest.create(request).getBody()

        then:
        EmployeeViewAssertion.assertEmployeeView(underTest.findById(id).getBody())
                .hasId()
                .hasFirstName("firstName")
                .hasLastName("lastName")
                .hasEmail("email")
                .hasPhoneNumber("12345")
    }

    def 'should be able to delete employee from system'() {
        given:
        def request = new CreateEmployee("firstName", "lastName", "email", "12345")
        def employeeId = underTest.create(request).getBody()

        when:
        underTest.delete(employeeId)

        then:
        EmployeeViewAssertion.assertEmployeeView(underTest.findById(employeeId).getBody())
                .isNull()
    }

    def 'should be able to get all employee from system'() {
        given:
        def request_1 = new CreateEmployee("firstName_1", "lastName_1", "email_1", "12345_1")
        underTest.create(request_1).getBody()
        def request_2 = new CreateEmployee("firstName_2", "lastName_2", "email_2", "12345_2")
        underTest.create(request_2).getBody()

        when:
        List<EmployeeView> allEmployees = underTest.findAll().getBody()

        then:
        allEmployees.size() == 2
        def employee_1 = allEmployees.stream()
                .filter({ employee -> employee.getFirstName().equalsIgnoreCase("firstName_1") })
                .findAny()
                .get()
        EmployeeViewAssertion.assertEmployeeView(employee_1)
                .hasId()
                .hasFirstName("firstName_1")
                .hasLastName("lastName_1")
                .hasEmail("email_1")
                .hasPhoneNumber("12345_1")

        def employee_2 = allEmployees.stream()
                .filter({ employee -> employee.getFirstName().equalsIgnoreCase("firstName_2") })
                .findAny()
                .get()
        EmployeeViewAssertion.assertEmployeeView(employee_2)
                .hasId()
                .hasFirstName("firstName_2")
                .hasLastName("lastName_2")
                .hasEmail("email_2")
                .hasPhoneNumber("12345_2")
    }

    @Configuration
    @EnableMongoRepositories(basePackageClasses = EmployeeRepository.class)
    protected static class TestConfiguration {

        @Bean
        MongoTemplate mongoTemplate(MongoClient mongoClient) {
            return new MongoTemplate(mongoDbFactory(mongoClient))
        }

        @Bean
        MongoDbFactory mongoDbFactory(MongoClient mongoClient) {
            return new SimpleMongoDbFactory(mongoClient, "test")
        }

        @Bean(destroyMethod = "shutdown")
        MongoServer mongoServer() {
            MongoServer mongoServer = new MongoServer(new MemoryBackend())
            mongoServer.bind()
            return mongoServer
        }

        @Bean(destroyMethod = "close")
        MongoClient mongoClient(MongoServer mongoServer) {
            return new MongoClient(new ServerAddress(mongoServer.getLocalAddress()))
        }
    }
}
