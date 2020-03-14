package com.uniovi.tests;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;

//Ordenamos las pruebas por el nombre del método 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class LoginTest {

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
	 * Prueba 1. Inicio de sesión con datos válidos (administrador)
	 */
	@Test
	public void PR05() {
		// Vamos al formulario de inicio de sesión
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// Comprobamos que iniciamos sesión
		PO_HomeView.checkKey(driver, "welcome.message", PO_Properties.getSPANISH());
	}
	
	/**
	 * Prueba 2. Inicio de sesión con datos válidos (usuario estándar)
	 */
	@Test
	public void PR06() {
		// Vamos al formulario de inicio de sesión
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "ejemplo1@gmail.com", "123456");
		// Comprobamos que iniciamos sesión
		PO_LoginView.checkKey(driver, "welcome.message", PO_Properties.getSPANISH());
	}
	
	/**
	 * Prueba 3. Inicio de sesión con datos inválidos (usuario estándar, campo email y contraseña vacíos).
	 */
	@Test
	public void PR07() {
		// Vamos al formulario de inicio de sesión
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "", "");
		// Comprobamos que no iniciamos sesión
		PO_LoginView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}
	
	/**
	 * Prueba 3. Inicio de sesión con datos válidos (usuario estándar, email existente, pero contraseña incorrecta).
	 */
	@Test
	public void PR08() {
		// Vamos al formulario de inicio de sesión
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "ejemplo1@gmail.com", "aaaaa");
		// Comprobamos que no iniciamos sesión (se muestra mensaje de error)
		PO_LoginView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
		PO_LoginView.checkKey(driver, "login.error.message", PO_Properties.getSPANISH());
	}
}