package com.tallerwebi.Infraestructura;

import com.tallerwebi.dominio.RepositorioTienda;
import com.tallerwebi.dominio.Tienda;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioTiendaTest {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    RepositorioTienda repositorioTienda;

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnaTienda() {
        givenNoExisteTienda();
        Tienda tienda = new Tienda();
        tienda.setNombre("Mundo De Juguetes");
        tienda.setTelefono("123456789");
        tienda.setEmail("contacto@mundojuguetes.com");
        Tienda guardada = whenGuardoTienda(tienda);
        thenTiendaGuardada(guardada);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanListarLasTiendas() {
        givenExisteTienda("Mundo De Juguetes");
        givenExisteTienda("Cebra");
        givenExisteTienda("Playroom");

        List<Tienda> list = whenBuscoTiendas();

        thenEncuentroTiendas(list);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaActualizarUnaTienda() {
        Tienda tienda = new Tienda();
        tienda.setNombre("Original Store");
        tienda.setTelefono("123456789");
        tienda.setEmail("contacto@mundojuguetes.com");

        whenGuardoTienda(tienda);

        Long idOriginal = tienda.getId();

        Tienda tiendaActualizada = repositorioTienda.obtenerTiendaPorId(idOriginal);
        tiendaActualizada.setEmail("updated@store.com");

        Tienda tiendaBuscada = repositorioTienda.obtenerTiendaPorId(idOriginal);

        thenTiendaActualizada(tiendaBuscada);

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaEncontrarUnaTiendaPorId(){
        Tienda tienda = new Tienda();
        tienda.setNombre("Original Store");
        tienda.setTelefono("123456789");
        tienda.setEmail("contacto@mundojuguetes.com");

        repositorioTienda.guardarTienda(tienda);
        Tienda tBuscada = whenBuscoTiendaPorId(tienda.getId());
        thenEncuentroTiendaPorId(tBuscada);

    }

//    PRIVATE

    private void thenTiendaActualizada(Tienda tiendaBuscada) {
        assertNotNull(tiendaBuscada);
        assertThat(tiendaBuscada.getNombre(), equalTo("Original Store"));
        assertThat(tiendaBuscada.getTelefono(), equalTo("123456789"));
        assertThat(tiendaBuscada.getEmail(), equalTo("updated@store.com"));
    }

    private void thenEncuentroTiendaPorId(Tienda tiendaBuscada) {
        assertNotNull(tiendaBuscada);
        assertThat(tiendaBuscada.getNombre(), equalTo("Original Store"));
        assertThat(tiendaBuscada.getId(), equalTo(1L));
    }

    private Tienda whenBuscoTiendaPorId(Long idTienda) {
        return repositorioTienda.obtenerTiendaPorId(idTienda);
    }

    private void thenEncuentroTiendas(List<Tienda> list) {
        assertThat(list.size(), equalTo(3));
    }

    private List<Tienda> whenBuscoTiendas() {
        return repositorioTienda.obtenerListadoDeTiendas();
    }

    private void givenExisteTienda(String nombre) {
        Tienda tienda = new Tienda();
        tienda.setNombre(nombre);
        repositorioTienda.guardarTienda(tienda);
    }

    private void thenTiendaGuardada(Tienda guardada) {
        assertNotNull(guardada);
        assertThat(guardada.getNombre(), equalTo("Mundo De Juguetes"));
        assertThat(guardada.getTelefono(), equalTo("123456789"));
        assertThat(guardada.getEmail(), equalTo("contacto@mundojuguetes.com"));
        assertNotNull(guardada.getId());
    }

    private Tienda whenGuardoTienda(Tienda tienda){
        this.repositorioTienda.guardarTienda(tienda);
        return this.repositorioTienda.obtenerTiendaPorId(tienda.getId());
    }

    private void givenNoExisteTienda() {

    }


}
