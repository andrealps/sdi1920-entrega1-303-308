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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PostsView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Tests {

	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizaciones
	// automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\SARA\\Documents\\Universidad\\3º Curso\\SDI\\Práctica\\Sesión5-Web testing con Selenium\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";

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
		//driver.quit();
	}

	/**
	 * [Prueba1] Registro de Usuario con datos válidos
	 */
	@Test
	public void PR01() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "maria@gmail.com", "María", "Perez", "77777", "77777");
		// Comprobamos que entramos en la sección privada
		PO_RegisterView.checkKey(driver, "welcome.message", PO_Properties.getSPANISH());

	}

	/**
	 * [Prueba2] Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	 * apellidos vacíos) Prueba caso NEGATIVO
	 */
	@Test
	public void PR02() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "", "", "", "77777", "77777");
		// Comprobamos que seguimos en el registro
		PO_RegisterView.checkKey(driver, "signup.message", PO_Properties.getSPANISH());
	}

	/**
	 * [Prueba3] Registro de Usuario con datos inválidos (Repeticion de contraseña
	 * inválida) Prueba caso POSITIVO
	 */
	@Test
	public void PR03() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "a@h.com", "Ejemplo", "Ejemplo2", "77777", "77771");
		// Comprobamos el error de contraseña inválida
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	/**
	 * [Prueba4] Registro de Usuario con datos inválidos (Email existente) Prueba
	 * caso POSITIVO
	 */
	@Test
	public void PR04() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "ejemplo1@gmail.com", "Ejemplo", "Ejemplo2", "77777", "77777");
		// Comprobamos el error de email repetido
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
	}

	/**
	 * [Prueba5] Inicio de sesión con datos válidos (administrador)
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
	 * [Prueba6] Inicio de sesión con datos válidos (usuario estándar)
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
	 * [Prueba7] Inicio de sesión con datos inválidos (usuario estándar, campo email
	 * y contraseña vacíos).
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
	 * [Prueba8] Inicio de sesión con datos válidos (usuario estándar, email
	 * existente, pero contraseña incorrecta).
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

	/**
	 * [Prueba9] Hacer click en la opción de salir de sesión y comprobar que se
	 * redirige a la página de inicio de sesión (Login).
	 */
	@Test
	public void PR09() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "sara@gmail.com", "Saray", "Garcia", "8888888", "8888888");
		// Comprobamos que entramos en la sección privada
		PO_LoginView.checkKey(driver, "welcome.message", PO_Properties.getSPANISH());
		// Salimos de sesión
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// Comprobamos que estamos en la página de login
		PO_LoginView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}

	/**
	 * [Prueba10] Comprobar que el botón cerrar sesión no está visible si el usuario
	 * no está autenticado.
	 */
	@Test
	public void PR10() {
		// Comprobamos que el botón de cerrar sesión no está disponible al no tener la
		// sesión iniciada
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");
	}

	/**
	 * [Prueba11] Mostrar el listado de usuarios y comprobar que se muestran todos
	 * los que existen en el sistema
	 */
	@Test
	public void PR11() {
		// Nos logueamos
		PO_PrivateView.login(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a lista de usuarios li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Se cargan todos los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 5);
		// Nos vamos a la siguiente página
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elementos.get(2).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 3);
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	/**
	 * [Prueba12] Hacer una búsqueda con el campo vacío y comprobar que se muestra
	 * la página que corresponde con el listado usuarios existentes en el sistema.
	 */
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
		// Nos vamos a la siguiente página
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elementos.get(2).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 3);
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	/**
	 * [Prueba13] Hacer una búsqueda escribiendo en el campo un texto que no exista
	 * y comprobar que se muestra la página que corresponde, con la lista de
	 * usuarios vacía.
	 */

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

	/**
	 * [Prueba14] Hacer una búsqueda con un texto específico y comprobar que se
	 * muestra la página que corresponde, con la lista de usuarios en los que el
	 * texto especificados sea parte de su nombre, apellidos o de su email.
	 */
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

	/**
	 * [Prueba15] Desde el listado de usuarios de la aplicación, enviar una
	 * invitación de amistad a un usuario. Comprobar que la solicitud de amistad
	 * aparece en el listado de invitaciones
	 */
	@Test
	public void PR15() {
		// Nos logueamos
		PO_PrivateView.login(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a lista de usuarios li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Enviamos peticion
		elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'Lucas')]/following-sibling::*/a[contains(@href, 'friend/add')]");
		elementos.get(0).click();
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
		// Nos logueamos con el ususario al que le enviamos la peticion
		PO_PrivateView.login(driver, "ejemplo2@gmail.com", "123456");
		// Vamos a la pestaña amigos
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de solicitud de amistad
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listRequests')]");
		elementos.get(0).click();
		// Comprobamos que hay una solicitud de el usuario ejemplo1: Pedro
		SeleniumUtils.textoPresentePagina(driver, "Pedro");
		// Nos deconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");

	}

	/**
	 * [Prueba16] Desde el listado de usuarios de la aplicación, enviar una
	 * invitación de amistad a un usuario al que ya le habíamos enviado la
	 * invitación previamente. No debería dejarnos enviar la invitación.
	 */
	@Test
	public void PR16() {
		// Nos logueamos
		PO_PrivateView.login(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a lista de usuarios li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Buscamos el nombre especifico
		WebElement texto = driver.findElement(By.name("searchText"));
		texto.click();
		texto.clear();
		texto.sendKeys("luc");
		By boton = By.className("btn");
		driver.findElement(boton).click();
		// Comprobamos que aparece el usuario que corresponde
		elementos = PO_View.checkElement(driver, "text", "ejemplo2@gmail.com");
		// Comprobamos que no aparece el boton de agregar amigo junto al usuario ya que
		// ya le enviamos peticion
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Agregar amigo", PO_View.getTimeout());
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	/**
	 * [Prueba17] Mostrar el listado de invitaciones de amistad recibidas
	 */
	@Test
	public void PR17() {
		// Enviamos 3 peticiones con varios usuarios al ejemplo3@gmail.com(Maria)
		PO_PrivateView.enviarPeticiones(driver);
		// Nos logueamos con ese usuario
		PO_PrivateView.login(driver, "ejemplo3@gmail.com", "123456");
		// Vamos a solicitudes de amistad
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de solicitud de amistad
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listRequests')]");
		elementos.get(0).click();
		// Se cargan todos los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 3);
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	/**
	 * [Prueba18] Sobre el listado de invitaciones recibidas. Hacer click en el
	 * botón/enlace de una de ellas y comprobar que dicha solicitud desaparece del
	 * listado de invitaciones.
	 */
	@Test
	public void PR18() {
		// Nos logueamos con ese usuario
		PO_PrivateView.login(driver, "ejemplo3@gmail.com", "123456");
		// Vamos a la pestaña de amigos
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de solicitud de amistad
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listRequests')]");
		elementos.get(0).click();
		// Se cargan todos los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		// Aceptamos peticion de Jaime
		elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'Jaime')]/following-sibling::*/a[contains(@href, 'user/accept')]");
		elementos.get(0).click();
		// Comprobamos que no esta
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Jaime", PO_View.getTimeout());
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	/**
	 * [Prueba19] Mostrar el listado de amigos de un usuario. Comprobar que el
	 * listado contiene los amigos que deben ser.
	 */
	@Test
	public void PR19() {
		// Nos logueamos
		PO_PrivateView.login(driver, "ejemplo3@gmail.com", "123456");
		// Vamos a la pestaña de amigos
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de solicitud de amistad
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listRequests')]");
		elementos.get(0).click();
		// Se cargan todos los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		// Aceptamos peticion de Andrea (ya habiamos aceptado a Jaime)
		elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'Andrea')]/following-sibling::*/a[contains(@href, 'user/accept')]");
		elementos.get(0).click();
		// Vamos a la pestaña de amigos
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de amigos
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listFriends')]");
		elementos.get(0).click();
		// Se cargan todos los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 2);
	}

	/**
	 * [Prueba20] Pruebas de internacionalización 1. Página de registro 2. Página de
	 * login 3. Página de Lista de usuarios 4. Página de Lista de amigos
	 */
	@Test
	public void PR20() {

		///////////////////////// REGISTRO /////////////////////////

		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Comprobamos que el título de la página está en español
		PO_RegisterView.checkKey(driver, "signup.message", PO_Properties.getSPANISH());
		// Cambio de idioma de Español a Ingles
		PO_HomeView.changeIdiom(driver, "btnEnglish");
		// Comprobamos que cambia el idioma de la página de registro
		PO_RegisterView.checkKey(driver, "signup.message", PO_Properties.getENGLISH());

		///////////////////////// LOGIN /////////////////////////

		// Cambio de idioma de Ingles a Español
		PO_HomeView.changeIdiom(driver, "btnSpanish");
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Comprobamos que el título de la página está en español
		PO_LoginView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
		// Cambio de idioma de Español a Ingles
		PO_HomeView.changeIdiom(driver, "btnEnglish");
		// Comprobamos que cambia el idioma de la página de login
		PO_LoginView.checkKey(driver, "login.message", PO_Properties.getENGLISH());

		///////////////////////// LISTA DE USUARIOS /////////////////////////

		// Cambio de idioma de Ingles a Español
		PO_HomeView.changeIdiom(driver, "btnSpanish");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a la lista de usuarios
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Comprobamos que el título de la página está en español
		PO_NavView.checkKey(driver, "listUser.message", PO_Properties.getSPANISH());
		// Cambio de idioma de Español a Ingles
		PO_HomeView.changeIdiom(driver, "btnEnglish");
		// Comprobamos que cambia el idioma de la página de registro
		PO_NavView.checkKey(driver, "listUser.message", PO_Properties.getENGLISH());

		///////////////////////// SOLICITUDES DE AMISTAD /////////////////////////

		// Cambio de idioma de Ingles a Español
		PO_HomeView.changeIdiom(driver, "btnSpanish");
		// Vamos al menú de amigos
		List<WebElement> elementos2 = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos2.get(0).click();
		// Esperamos a que aparezca la pestaña de solicitud de amistad
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listRequests')]");
		elementos.get(0).click();
		// Comprobamos que el título de la página está en español
		PO_NavView.checkKey(driver, "friendsRequest.introduction", PO_Properties.getSPANISH());
		// Cambio de idioma de Español a Ingles
		PO_HomeView.changeIdiom(driver, "btnEnglish");
		// Comprobamos que cambia el idioma de la página de peticiones de amistad
		PO_NavView.checkKey(driver, "friendsRequest.introduction", PO_Properties.getENGLISH());
	}

	/**
	 * [Prueba21] Intentar acceder sin estar autenticado a la opción de listado de
	 * usuarios. Se deberá volver al formulario de login
	 */
	@Test
	public void PR21() {
		// Intentamos acceder al listado de usuarios
		driver.navigate().to("http://localhost:8090/user/listUsers");
		// Comprobamos que nos redirige a la página de inicio de sesión
		PO_LoginView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}

	/**
	 * [Prueba22] Intentar acceder sin estar autenticado a la opción de listado de
	 * publicaciones de un usuario estándar. Se deberá volver al formulario de login
	 */
	@Test
	public void PR22() {
		// Intentamos acceder al listado de usuarios
		driver.navigate().to("http://localhost:8090/post/listPost");
		// Comprobamos que nos redirige a la página de inicio de sesión
		PO_LoginView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}

	/**
	 * [Prueba23] Estando autenticado como usuario estándar intentar acceder a una
	 * opción disponible solo para usuarios administradores (Se puede añadir una
	 * opción cualquiera en el menú). Se deberá indicar un mensaje de acción
	 * prohibida.
	 */
	@Test
	public void PR23() {
		// Nos logueamos como usuario estándar
		PO_PrivateView.login(driver, "ejemplo1@gmail.com", "123456");
		// Vamos a la pestaña gestion de usuariosli[contains(@id, 'admin-test')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'admin-test')]/a");
		elementos.get(0).click();
		// Comprobamos que nos sale un mensaje de accion prohibida
		SeleniumUtils.textoPresentePagina(driver, "HTTP Status 403 – Forbidden");
	}

	/**
	 * [Prueba24] Ir al formulario crear publicaciones, rellenarla con datos válidos
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
		PO_PostsView.checkKey(driver, "listPost.introduction", PO_Properties.getSPANISH());
	}

	/**
	 * [Prueba25] Ir al formulario de crear publicaciones, rellenarla con datos
	 * inválidos (campo título vacío) y pulsar el botón Submit. Comprobar que se
	 * muestra el mensaje de campo obligatorio.
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
		PO_PostsView.checkKey(driver, "addPost.title.error", PO_Properties.getSPANISH());
		PO_PostsView.checkKey(driver, "addPost.text.error", PO_Properties.getSPANISH());
	}

	/**
	 * [Prueba26] Mostrar el listado de publicaciones de un usuario y comprobar que
	 * se muestran todas las que existen para dicho usuario.
	 */
	@Test
	public void PR26() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Iniciamos sesión con un usuario registrado
		PO_LoginView.fillForm(driver, "ejemplo5@gmail.com", "123456");
		// Vamos a la opción de publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'posts-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de listas de publicaciones
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/listPost')]");
		elementos.get(0).click();
		// Comprobar que hay 1 publicacion
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "class", "panel-info", PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
	}

	/**
	 * [Prueba27] Mostrar el listado de publicaciones de un usuario amigo y
	 * comprobar que se muestran todas las que existen para dicho usuario.
	 * 
	 */
	@Test
	public void PR27() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Iniciamos sesión con un usuario registrado
		PO_LoginView.fillForm(driver, "ejemplo4@gmail.com", "123456");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de lista de amigos
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listFriends')]");
		elementos.get(0).click();
		// Comprobamos que estamos en la página de amigos mirando que se encuentre a la
		// usuaria Andrea
		SeleniumUtils.textoPresentePagina(driver, "Andrea");
		// Accedemos a las publicaciones del usuario5
		elementos = PO_View.checkElement(driver, "text", "Andrea");
		elementos.get(0).click();
		assertTrue(elementos.size() == 1);
	}

	/**
	 * [Prueba28] Utilizando un acceso vía URL u otra alternativa, tratar de listar
	 * las publicaciones de un usuario que no sea amigo del usuario identificado en
	 * sesión. Comprobar que el sistema da un error de autorización.
	 */
	@Test
	public void PR28() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Iniciamos sesión con un usuario registrado
		PO_LoginView.fillForm(driver, "ejemplo4@gmail.com", "123456");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la pestaña de lista de amigos
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listFriends')]");
		elementos.get(0).click();
		// Comprobamos que estamos en la página de amigos mirando que se encuentre a la
		// usuaria Andrea
		SeleniumUtils.textoPresentePagina(driver, "Andrea");
		// Intentamos acceder a las publicaciones del usuario2 (no es amigo del usuario
		// 5)
		driver.navigate().to("http://localhost:8090/post/listPost/ejemplo4@gmail.com");
		// Comprobamos que nos redirige a la pestaña de mis amigos
		SeleniumUtils.textoPresentePagina(driver, "Andrea");
	}

	/**
	 * [Prueba 29] Desde el formulario de crear publicaciones, crear una publicación
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
	 * [Prueba30] Crear una publicación con datos válidos y sin una foto adjunta.
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
		// Comprobamos que la publicación se ha creado con éxito, ya que la foto no es
		// obligatoria
		SeleniumUtils.textoPresentePagina(driver, "Vacaciones");
		SeleniumUtils.textoPresentePagina(driver, LocalDate.now().toString());
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
		assertTrue(elementos.size() == 9);
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	/**
	 * [Prueba32] Ir a la lista de usuarios, borrar el primer usuario de la lista,
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
	 * [Prueba33] Ir a la lista de usuarios, borrar el último usuario de la lista,
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
		// Comprobamos que el usuario Saray no esta en la lista
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Saray", PO_View.getTimeout());
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Email");
	}

	/**
	 * [Prueba34] Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la
	 * lista se actualiza y dicho usuario desaparece.
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