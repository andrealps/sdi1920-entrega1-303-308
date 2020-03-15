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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListaPublicacionesAmigoTest {

	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizaciones
	// automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "D:\\UNIVERSIDAD\\Tercer curso\\SDI\\Material adicional\\geckodriver024win64.exe";

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

	/**
	 * Prueba 27. Mostrar el listado de publicaciones de un usuario amigo y
	 * comprobar que se muestran todas las que existen para dicho usuario.
	 * 
	 */
	@Test
	public void PR27() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Iniciamos sesión con un usuario registrado
		PO_LoginView.fillForm(driver, "ejemplo4@gmail.com", "123456");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de lista de amigos
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listFriends')]");
		elementos.get(0).click();
		// Comprobamos que estamos en la página de amigos mirando que se encuentre a la
		// usuaria Andrea
		SeleniumUtils.textoPresentePagina(driver, "Andrea");
		// Accedemos a las publicaciones del usuario5
		elementos = PO_View.checkElement(driver, "text", "Andrea");
		elementos.get(0).click();
		assertTrue(elementos.size() == 1);
	}

	/**
	 * Prueba 28. Utilizando un acceso vía URL u otra alternativa, tratar de listar
	 * las publicaciones de un usuario que no sea amigo del usuario identificado en
	 * sesión. Comprobar que el sistema da un error de autorización.
	 * 
	 */
	@Test
	public void PR28() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Iniciamos sesión con un usuario registrado
		PO_LoginView.fillForm(driver, "ejemplo4@gmail.com", "123456");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de lista de amigos
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listFriends')]");
		elementos.get(0).click();
		// Comprobamos que estamos en la página de amigos mirando que se encuentre a la
		// usuaria Andrea
		SeleniumUtils.textoPresentePagina(driver, "Andrea");
		// Intentamos acceder a las publicaciones del usuario2 (no es amigo del usuario 5)
		driver.navigate().to("http://localhost:8090/post/listPost/ejemplo4@gmail.com");
		// Comprobamos que nos redirige a la pestaña de mis amigos
		SeleniumUtils.textoPresentePagina(driver, "Andrea");
	}
}
