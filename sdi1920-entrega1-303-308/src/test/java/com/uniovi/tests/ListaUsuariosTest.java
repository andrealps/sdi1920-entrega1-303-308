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

public class ListaUsuariosTest {

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

	// [Prueba12]
	// Hacer una búsqueda con el campo vacío y comprobar que se muestra la página
	// que corresponde con el listado usuarios existentes en el sistema.
	@Test
	public void PR12() {
		// Nos logueamos
		PO_PrivateView.login(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a lista de usuarios li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Busqueda con campo vacío
		WebElement texto = driver.findElement(By.name("searchText"));
		texto.click();
		texto.clear();
		texto.sendKeys("");
		By boton = By.className("btn");
		driver.findElement(boton).click();
		// Se cargan todos los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 5);
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	// [Prueba13]
	// Hacer una búsqueda escribiendo en el campo un texto que no exista y
	// comprobar que se muestra la página que corresponde, con la lista de usuarios
	// vacía.
	@Test
	public void PR13() {
		// Nos logueamos
		PO_PrivateView.login(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a lista de usuarios li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Busqueda con campo vacío
		WebElement texto = driver.findElement(By.name("searchText"));
		texto.click();
		texto.clear();
		texto.sendKeys("prueba");
		By boton = By.className("btn");
		driver.findElement(boton).click();
		// Y esperamos a que NO aparezca nada
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "@gmail.com", PO_View.getTimeout());
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	// [Prueba14]
	// Hacer una búsqueda con un texto específico y comprobar que se muestra la
	// página que corresponde,
	// con la lista de usuarios en los que el texto especificados sea parte de su
	// nombre, apellidos o de su email.
	@Test
	public void PR14() {
		// Nos logueamos
		PO_PrivateView.login(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a lista de usuarios li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Busqueda con texto especifico
		WebElement texto = driver.findElement(By.name("searchText"));
		texto.click();
		texto.clear();
		texto.sendKeys("luc");
		By boton = By.className("btn");
		driver.findElement(boton).click();
		// Comprobamos que aparece el usuario que corresponde
		elementos = PO_View.checkElement(driver, "text", "ejemplo2@gmail.com");
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}
}
