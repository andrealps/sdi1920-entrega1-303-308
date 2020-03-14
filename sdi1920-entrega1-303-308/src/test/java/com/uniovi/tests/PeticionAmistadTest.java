package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PeticionAmistadTest {

	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizaciones
	// automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\SARA\\Documents\\Universidad\\3º Curso\\SDI\\Práctica\\Sesión5-Web testing con Selenium\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";
	// En MACOSX (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas):
	// static String PathFirefox65 =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// static String Geckdriver024 = "/Users/delacal/selenium/geckodriver024mac";
	// Común a Windows y a MACOSX

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// [Prueba15]
	// Desde el listado de usuarios de la aplicación, enviar una invitación de
	// amistad a un usuario.
	// Comprobar que la solicitud de amistad aparece en el listado de invitaciones
	@Test
	public void PR15() {
		// Nos logueamos
		PO_PrivateView.login(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a lista de usuarios li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Enviamos peticion
		elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'Lucas')]/following-sibling::*/a[contains(@href, 'friend/add')]");
		elementos.get(0).click();
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
		// Nos logueamos con el ususario al que le enviamos la peticion
		PO_PrivateView.login(driver, "ejemplo2@gmail.com", "123456");
		// Vamos a la pestaña amigos
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de solicitud de amistad
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listRequests')]");
		elementos.get(0).click();
		// Comprobamos que hay una solicitud de el usuario ejemplo1: Pedro
		SeleniumUtils.textoPresentePagina(driver, "Pedro");
		// Nos deconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");

	}

	// [Prueba16]
	// Desde el listado de usuarios de la aplicación, enviar una invitación de
	// amistad a un usuario al que ya le habíamos enviado la invitación previamente.
	// No debería dejarnos enviar la invitación.
	@Test
	public void PR16() {
		// Nos logueamos
		PO_PrivateView.login(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a lista de usuarios li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Buscamos el nombre especifico
		WebElement texto = driver.findElement(By.name("searchText"));
		texto.click();
		texto.clear();
		texto.sendKeys("luc");
		By boton = By.className("btn");
		driver.findElement(boton).click();
		// Comprobamos que aparece el usuario que corresponde
		elementos = PO_View.checkElement(driver, "text", "ejemplo2@gmail.com");
		// Comprobamos que no aparece el boton de agregar amigo junto al usuario ya que
		// ya le enviamos peticion
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Agregar amigo", PO_View.getTimeout());
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	// [Prueba 17]
	// Mostrar el listado de invitaciones de amistad recibidas
	@Test
	public void PR17() {
		// Enviamos 3 peticiones con varios usuarios al ejemplo3@gmail.com(Maria)
		PO_PrivateView.enviarPeticiones(driver);
		// Nos logueamos con ese usuario
		PO_PrivateView.login(driver, "ejemplo3@gmail.com", "123456");
		// Vamos a solicitudes de amistad
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de solicitud de amistad
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listRequests')]");
		elementos.get(0).click();
		// Se cargan todos los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 3);
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	// [Prueba18]
	// Sobre el listado de invitaciones recibidas. Hacer click en el botón/enlace de
	// una de ellas y
	// comprobar que dicha solicitud desaparece del listado de invitaciones.
	@Test
	public void PR18() {
		// Nos logueamos con ese usuario
		PO_PrivateView.login(driver, "ejemplo3@gmail.com", "123456");
		// Vamos a la pestaña de amigos
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de solicitud de amistad
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listRequests')]");
		elementos.get(0).click();
		// Se cargan todos los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		// Aceptamos peticion de Jaime
		elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'Jaime')]/following-sibling::*/a[contains(@href, 'user/accept')]");
		elementos.get(0).click();
		// Comprobamos que no esta
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Jaime", PO_View.getTimeout());
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	// [Prueba19]
	// Mostrar el listado de amigos de un usuario.
	// Comprobar que el listado contiene los amigos que deben ser.
	@Test
	public void PR19() {
		// Nos logueamos
		PO_PrivateView.login(driver, "ejemplo3@gmail.com", "123456");
		// Vamos a la pestaña de amigos
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de solicitud de amistad
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listRequests')]");
		elementos.get(0).click();
		// Se cargan todos los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		// Aceptamos peticion de Andrea (ya habiamos aceptado a Jaime)
		elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'Andrea')]/following-sibling::*/a[contains(@href, 'user/accept')]");
		elementos.get(0).click();
		// Vamos a la pestaña de amigos
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de amigos
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listFriends')]");
		elementos.get(0).click();
		// Se cargan todos los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 2);
	}

}
