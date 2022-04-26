package fi.triforce.TicketGuru.salesevent;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Validator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fi.triforce.TicketGuru.Domain.Event;
import fi.triforce.TicketGuru.Domain.EventRepository;
import fi.triforce.TicketGuru.Domain.SalesEvent;
import fi.triforce.TicketGuru.Domain.SalesEventRepository;
import fi.triforce.TicketGuru.Domain.TicketRepository;
import fi.triforce.TicketGuru.Domain.TicketType;
import fi.triforce.TicketGuru.Domain.TicketTypeRepository;
import fi.triforce.TicketGuru.Web.service.SalesService;
import fi.triforce.TicketGuru.dto.SalesObject;
import fi.triforce.TicketGuru.exception.TooManyTicketsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SalesServiceTest {
    @Autowired
    private TicketTypeRepository ttr;
    @Autowired
    private SalesEventRepository ser;
    @Autowired
    private TicketRepository tr;
    @Mock
    private Validator validator;

    @InjectMocks
    private SalesService ss;

    @BeforeEach
    void setUp() {
        ss = new SalesService(ttr, ser, tr, validator, 0);
    }

    @Test
    void creatingSaleCreatesSale() {
        TicketType tt = ttr.findById((long) 1).get();
        SalesObject salesObject = new SalesObject(tt.getTicketTypeId(), 5, 0, BigDecimal.valueOf(10));
        SalesObject salesObjectTwo = new SalesObject(tt.getTicketTypeId(), 3, 0, BigDecimal.valueOf(10));
        List<SalesObject> list = List.of(salesObject, salesObjectTwo);

        assertThat(ss.createSale(list).getClass()).isEqualTo(SalesEvent.class);
    }

    @Test
    void creatingSaleWithTooManyTicketsFailsToCreateSale() {
        TicketType tt = ttr.findById((long) 1).get();
        SalesObject salesObject = new SalesObject(tt.getTicketTypeId(), 5, 0, BigDecimal.valueOf(10));
        SalesObject salesObjectTwo = new SalesObject(tt.getTicketTypeId(), 4000, 0, BigDecimal.valueOf(10));
        List<SalesObject> list = List.of(salesObject, salesObjectTwo);

        assertThatThrownBy(() -> { ss.createSale(list);}).isInstanceOf(TooManyTicketsException.class);
    }
}
