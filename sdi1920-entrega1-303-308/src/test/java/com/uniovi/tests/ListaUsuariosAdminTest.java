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

public class ListaUsuariosAdminTest {

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
		// driver.quit();
	}

	/**
	 * [Prueba31] Mostrar el listado de usuarios y comprobar que se muestran todos
	 * los que existen en el sistema.
	 */
	@Test
	public void PR31() {
		// Nos logueamos
		PO_PrivateView.login(driver, "admin@email.com", "admin");
		// Vamos a lista de usuarios li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'admin-menu')]/a");
		elementos.get(0).click();
		// Se cargan todos los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 7);
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	/**
	 * [Prueba 32] Ir a la lista de usuarios, borrar el primer usuario de la lista,
	 * comprobar que la lista se actualiza y dicho usuario desaparece.
	 */
	@Test
	public void PR32() {
		// Nos logueamos
		PO_PrivateView.login(driver, "admin@email.com", "admin");
		// Vamos a lista de usuarios li[contains(@id, 'admin-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'admin-menu')]/a");
		elementos.get(0).click();
		// Seleccionamos el checkbox del primer usuario
		WebElement checkboxField = driver.findElement(By.tagName(("input")));
		checkboxField.click();
		// Damos al boton de eliminar
		By boton = By.className("btn");
		driver.findElement(boton).click();
		// Comprobamos que el usuario Pedro no esta en la lista
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Pedro", PO_View.getTimeout());
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	/**
	 * [Prueba 33] Ir a la lista de usuarios, borrar el último usuario de la lista,
	 * comprobar que la lista se actualiza y dicho usuario desaparece.
	 */
	@Test
	public void PR33() {
		// Nos logueamos
		PO_PrivateView.login(driver, "admin@email.com", "admin");
		// Vamos a lista de usuarios li[contains(@id, 'admin-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'admin-menu')]/a");
		elementos.get(0).click();
		// Seleccionamos el checkbox del ultimos usuario
		elementos = driver.findElements(By.xpath("//input[@type='checkbox']"));
		for (int i = elementos.size() - 1; i >= elementos.size() - 1; i--)
			elementos.get(i).click();
		// Damos al boton de eliminar
		By boton = By.className("btn");
		driver.findElement(boton).click();
		// Comprobamos que el usuario Carmen no esta en la lista
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Carmen", PO_View.getTimeout());
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	/**
	 * [Prueba 34] Ir a la lista de usuarios, borrar 3 usuarios,
	 * comprobar que la lista se actualiza y dicho usuario desaparece.
	 */
	@Test
	public void PR34() {
		// Nos logueamos
		PO_PrivateView.login(driver, "admin@email.com", "admin");
		// Vamos a lista de usuarios li[contains(@id, 'admin-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'admin-menu')]/a");
		elementos.get(0).click();
		// Seleccionamos el checkbox del ultimos usuario
		elementos = driver.findElements(By.xpath("//input[@type='checkbox']"));
		for (int i = 0; i < 3; i++)
			elementos.get(i).click();
		// Damos al boton de eliminar
		By boton = By.className("btn");
		driver.findElement(boton).click();
		// Comprobamos que los usuarios no estan en la lista
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Lucas", PO_View.getTimeout());
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Maria", PO_View.getTimeout());
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Jaime", PO_View.getTimeout());
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

}
