package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
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
import com.uniovi.tests.pageobjects.PO_PostsView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FotoPublicacionTest {

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
	 * Prueba 29. Desde el formulario de crear publicaciones, crear una publicación
	 * con datos válidos y una foto adjunta. Comprobar que en el listado de
	 * publicaciones aparece la foto adjunta junto al resto de datos de la
	 * publicación.
	 * 
	 */
	@Test
	public void PR29() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Iniciamos sesión con un usuario registrado
		PO_LoginView.fillForm(driver, "ejemplo2@gmail.com", "123456");
		// Vamos a la opción de publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'posts-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de crear publicación
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/addPost')]");
		elementos.get(0).click();
		// Rellenamos una publicación con datos válidos
		PO_PostsView.fillForm(driver, "Oviedo", "Ayuntamiento", "static/img/prueba.jpg");
		// Comprobamos que nos redirige a la página de lista de publicaciones
		PO_PostsView.checkKey(driver, "listPost.introduction", PO_Properties.getSPANISH());
		// Comprobamos que se encuentra la imagen
		elementos = PO_View.checkElement(driver, "free", "//img[contains(@class, 'img-thumbnail')]");
		assertTrue(elementos.size() == 1);
		// Comprobamos que se encuentra el título
		SeleniumUtils.textoPresentePagina(driver, "Oviedo");
		// Comprobamos que se encuentra la fecha
		SeleniumUtils.textoPresentePagina(driver, LocalDate.now().toString());
	}

	/**
	 * Test 30. Crear una publicación con datos válidos y sin una foto adjunta.
	 * Comprobar que la publicación se ha creado con éxito, ya que la foto no es
	 * obligaria.
	 */
	@Test
	public void PR30() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Iniciamos sesión con un usuario registrado
		PO_LoginView.fillForm(driver, "ejemplo2@gmail.com", "123456");
		// Vamos a la opción de publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'posts-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de crear publicación
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/addPost')]");
		elementos.get(0).click();
		// Rellenamos una publicación con datos válidos
		PO_PostsView.fillForm(driver, "Vacaciones", "En casa");
		// Comprobamos que nos redirige a la página de lista de publicaciones
		PO_PostsView.checkKey(driver, "listPost.introduction", PO_Properties.getSPANISH());
		// Comprobamos que la publicación se ha creado con éxito, ya que la foto no es obligatoria
		SeleniumUtils.textoPresentePagina(driver, "Vacaciones");
		SeleniumUtils.textoPresentePagina(driver, LocalDate.now().toString());
	}

}
