package com.uniovi.tests;

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
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;

//Ordenamos las pruebas por el nombre del método 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrearPublicacionTest {

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
	 * Prueba 24. Ir al formulario crear publicaciones, rellenarla con datos válidos
	 * y pulsar el botón Submit. Comprobar que la publicación sale en el listado de
	 * publicaciones de dicho usuario.
	 */
	@Test
	public void PR24() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Iniciamos sesión con un usuario registrado
		PO_LoginView.fillForm(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a la opción de publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'posts-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de crear publicación
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/addPost')]");
		elementos.get(0).click();
		// Rellenamos una publicación con datos válidos
		PO_PostsView.fillForm(driver, "Noche en la mon", "Aquí de fiesta");
		// Comprobamos que nos redirige a la página de lista de publicaciones
		PO_RegisterView.checkKey(driver, "listPost.introduction", PO_Properties.getSPANISH());
	}

	/**
	 * Prueba 25. Ir al formulario de crear publicaciones, rellenarla con datos
	 * inválidos (campo título vacío) y pulsar el botón Submit. Comprobar que se
	 * muestra el mensaje de campo obligatorio.
	 * 
	 */
	@Test
	public void PR25() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Iniciamos sesión con un usuario registrado
		PO_LoginView.fillForm(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a la opción de publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'posts-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de crear publicación
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/addPost')]");
		elementos.get(0).click();
		// Rellenamos una publicación con datos inválidos 
		PO_PostsView.fillForm(driver, "", "");
		// Comprobamos que se muestra el error de campos vacíos
		PO_RegisterView.checkKey(driver, "addPost.title.error", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "addPost.text.error", PO_Properties.getSPANISH());
	}
}
