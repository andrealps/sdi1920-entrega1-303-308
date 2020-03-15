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
public class ListaPublicacionesTest {

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
	 * Prueba 26. Mostrar el listado de publicaciones de un usuario y comprobar que
	 * se muestran todas las que existen para dicho usuario.
	 * 
	 */
	@Test
	public void PR26() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Iniciamos sesión con un usuario registrado
		PO_LoginView.fillForm(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a la opción de publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'posts-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de listas de publicaciones
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/listPost')]");
		elementos.get(0).click();
		// Comprobar que hay 7 publicaciones
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "class", "panel-info", PO_View.getTimeout());
		assertTrue(elementos.size() == 5);
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elementos.get(2).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "class", "panel-info", PO_View.getTimeout());
		assertTrue(elementos.size() == 2);
	}
}
