package com.uniovi.tests;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.*;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InternacionalizacionTest {
	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizaciones
	// automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "D:\\UNIVERSIDAD\\Tercer curso\\SDI\\Material adicional\\geckodriver024win64.exe";
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
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

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	/**
	 * PR20. Pruebas de internacionalización 1. Página de registro 2. Página de
	 * login 3. Página de Lista de usuarios 4. Página de Lista de amigos
	 */
	@Test
	public void PR20() {
		////////////////////////////////////// REGISTRO //////////////////////////////////////
		
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Comprobamos que el título de la página está en español
		PO_RegisterView.checkKey(driver, "signup.message", PO_Properties.getSPANISH());
		// Cambio de idioma de Español a Ingles
		PO_HomeView.changeIdiom(driver, "btnEnglish");
		// Comprobamos que cambia el idioma de la página de registro
		PO_RegisterView.checkKey(driver, "signup.message", PO_Properties.getENGLISH());

		////////////////////////////////////// LOGIN //////////////////////////////////////
		
		// Cambio de idioma de Ingles a Español
		PO_HomeView.changeIdiom(driver, "btnSpanish");
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Comprobamos que el título de la página está en español
		PO_RegisterView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
		// Cambio de idioma de Español a Ingles
		PO_HomeView.changeIdiom(driver, "btnEnglish");
		// Comprobamos que cambia el idioma de la página de login
		PO_RegisterView.checkKey(driver, "login.message", PO_Properties.getENGLISH());

		////////////////////////////////////// LISTA DE USUARIOS //////////////////////////////////////
		
		// Cambio de idioma de Ingles a Español
		PO_HomeView.changeIdiom(driver, "btnSpanish");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a la lista de usuarios
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Comprobamos que el título de la página está en español
		PO_RegisterView.checkKey(driver, "listUser.message", PO_Properties.getSPANISH());
		// Cambio de idioma de Español a Ingles
		PO_HomeView.changeIdiom(driver, "btnEnglish");
		// Comprobamos que cambia el idioma de la página de registro
		PO_RegisterView.checkKey(driver, "listUser.message", PO_Properties.getENGLISH());

		////////////////////////////////////// SOLICITUDES DE AMISTAD //////////////////////////////////////
		
		// Cambio de idioma de Ingles a Español
		PO_HomeView.changeIdiom(driver, "btnSpanish");
		// Vamos al menú de amigos
		List<WebElement> elementos2 = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos2.get(0).click();
		// Esperamos a que aparezca la pestaña de solicitud de amistad
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listRequests')]");
		elementos.get(0).click();
		
//		// Comprobamos que el título de la página está en español
//		PO_RegisterView.checkKey(driver, "friendsRequest.message", PO_Properties.getSPANISH());
//		// Cambio de idioma de Español a Ingles
//		PO_HomeView.changeIdiom(driver, "btnEnglish");
//		// Comprobamos que cambia el idioma de la página de peticiones de amistad
//		PO_RegisterView.checkKey(driver, "friendsRequest.message", PO_Properties.getENGLISH());
		
	}

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}
}
