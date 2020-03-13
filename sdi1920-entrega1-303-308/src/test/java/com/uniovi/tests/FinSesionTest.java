package com.uniovi.tests;

import static org.junit.Assert.assertTrue;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

public class FinSesionTest {
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
	 * Prueba 9. Hacer click en la opción de salir de sesión y comprobar que se
	 * redirige a la página de inicio de sesión (Login).
	 */
	@Test
	public void PR09() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "maria@gmail.com", "María", "Perez", "77777", "77777");
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "¡Bienvenidos al chat!");
		// Salimos de sesión
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// Comprobamos que estamos en la página de login
		PO_RegisterView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}
	
	/**
	 * Prueba 10. Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado.
	 */
	@Test
	public void PR10() {
		// Comprobamos que el botón de cerrar sesión no está disponible al no tener la sesión iniciada
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");
	}
}
