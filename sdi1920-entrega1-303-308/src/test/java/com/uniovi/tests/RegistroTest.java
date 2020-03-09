package com.uniovi.tests;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;

//Ordenamos las pruebas por el nombre del método 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class RegistroTest {

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
	 * Prueba 1. Registro de Usuario con datos válidos
	 */
	@Test
	public void prueba1() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "maria@gmail.com", "María", "Perez", "77777", "77777");
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "¡Bienvenidos al chat!");
	}

	/**
	 * Prueba 2. Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	 * apellidos vacíos)
	 * Prueba caso NEGATIVO
	 */
	@Test
	public void prueba2() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "", "", "", "77777", "77777");
		// Comprobamos que seguimos en el registro
		PO_RegisterView.checkKey(driver, "signup.message", PO_Properties.getSPANISH());
	}
	
	/**
	 * Prueba 3. Registro de Usuario con datos inválidos (Repeticiçon de contraseña inválida)
	 * Prueba caso POSITIVO
	 */
	@Test
	public void prueba3() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "a@h.com", "Ejemplo", "Ejemplo2", "77777", "77771");
		// Comprobamos el error de contraseña inválida
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}
	
	/**
	 * Prueba 4. Registro de Usuario con datos inválidos (Email existente)
	 * Prueba caso POSITIVO
	 */
	@Test
	public void prueba4() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "ejemplo1@gmail.com", "Ejemplo", "Ejemplo2", "77777", "77777");
		// Comprobamos el error de email repetido
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
	}
}