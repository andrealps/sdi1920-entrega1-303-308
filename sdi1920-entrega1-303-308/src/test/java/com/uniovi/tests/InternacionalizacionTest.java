package com.uniovi.tests;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.*;
import com.uniovi.tests.util.SeleniumUtils;

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

	// PR20. Pruebas de internacionalización
	@Test
	public void PR20() {
		// REGISTRO
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Comprobamos que el título de la página está en español
		PO_RegisterView.checkKey(driver, "signup.message", PO_Properties.getSPANISH());
		// Cambio de idioma de Español a Ingles
		PO_HomeView.changeIdiom(driver, "btnEnglish");
		// Comprobamos que cambia el idioma de la página de registro
		PO_RegisterView.checkKey(driver, "signup.message", PO_Properties.getENGLISH());
		
		// LOGIN
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
	}

//	// PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion
//	// = Nota A2.
//	// P13. Ver la lista de Notas.
//	@Test
//	public void PR13() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999990A", "123456");
//		// COomprobamos que entramos en la pagina privada de Alumno
//		PO_View.checkElement(driver, "text", "Notas del usuario");
//		SeleniumUtils.esperarSegundos(driver, 1);
//		// Contamos las notas
//		By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]");
//		driver.findElement(enlace).click();
//		SeleniumUtils.esperarSegundos(driver, 1);
//		// Esperamos por la ventana de detalle
//		PO_View.checkElement(driver, "text", "Detalles de la nota");
//		SeleniumUtils.esperarSegundos(driver, 1);
//		// Ahora nos desconectamos
//		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
//	}
//
//	// P14. Loguearse como profesor y Agregar Nota A2.
//	// P14. Esta prueba podría encapsularse mejor ...
//	@Test
//	public void PR14() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999993D", "123456");
//		// Comprobamos que entramos en la pagina privada del Profesor
//		PO_View.checkElement(driver, "text", "99999993D");
//		// Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
//		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
//		elementos.get(0).click();
//		// Esperamos a aparezca la opción de añadir nota: //a[contains(@href,
//		// 'mark/add')]
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/add')]");
//		// Pinchamos en agregar Nota.
//		elementos.get(0).click();
//		// Ahora vamos a rellenar la nota. //option[contains(@value, '4')]
//		PO_PrivateView.fillFormAddMark(driver, 3, "Nota Nueva 1", "8");
//		// Esperamos a que se muestren los enlaces de paginación la lista de notas
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
//		// Nos vamos a la última página
//		elementos.get(3).click();
//		// Comprobamos que aparece la nota en la pagina
//		elementos = PO_View.checkElement(driver, "text", "Nota Nueva 1");
//		// Ahora nos desconectamos
//		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
//	}
//
//	// PRN. Loguearse como profesor, vamos a la ultima página y Eliminamos la Nota
//	// Nueva 1.
//	// PRN. Ver la lista de Notas.
//	@Test
//	public void PR15() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999993D", "123456");
//		// Comprobamos que entramos en la pagina privada del Profesor
//		PO_View.checkElement(driver, "text", "99999993D");
//		// Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
//		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
//		elementos.get(0).click();
//		// Pinchamos en la opción de lista de notas.
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/list')]");
//		elementos.get(0).click();
//		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
//		// Nos vamos a la última página
//		elementos.get(3).click();
//		// Esperamos a que aparezca la Nueva nota en la ultima pagina
//		// Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
//		// td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(text(),
//		// 'mark/delete')]"
//		elementos = PO_View.checkElement(driver, "free",
//				"//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
//		elementos.get(0).click();
//		// Volvemos a la última pagina
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
//		elementos.get(3).click();
//		// Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva1", PO_View.getTimeout());
//		// Ahora nos desconectamos
//		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
//	}

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}
}
