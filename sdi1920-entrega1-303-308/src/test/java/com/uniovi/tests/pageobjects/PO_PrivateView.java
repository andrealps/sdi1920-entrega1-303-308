package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_PrivateView extends PO_NavView {

	static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep) {

		// Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 5);
		// Seleccionamos el alumnos userOrder
		new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
		// Rellenemos el campo de descripción
		WebElement description = driver.findElement(By.name("description"));
		description.clear();
		description.sendKeys(descriptionp);
		WebElement score = driver.findElement(By.name("score"));
		score.click();
		score.clear();
		score.sendKeys(scorep);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	static public void login(WebDriver driver, String email, String pass) {

		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, email, pass);
		// Comprobamos que entramos a la vista que “lista todos los usuarios de la
		// aplicación”
		PO_RegisterView.checkKey(driver, "welcome.message", PO_Properties.getSPANISH());
	}

	public static void enviarPeticiones(WebDriver driver) {
		login(driver, "ejemplo1@gmail.com", "123456");
		enviarPeticion(driver);
		PO_HomeView.clickOption(driver, "logout", "text", "Email");

		login(driver, "ejemplo3@gmail.com", "123456");
		enviarPeticion(driver);
		PO_HomeView.clickOption(driver, "logout", "text", "Email");

		login(driver, "ejemplo4@gmail.com", "123456");
		enviarPeticion(driver);
		PO_HomeView.clickOption(driver, "logout", "text", "Email");

		login(driver, "ejemplo5@gmail.com", "123456");
		enviarPeticion(driver);
		PO_HomeView.clickOption(driver, "logout", "text", "Email");

	}

	private static void enviarPeticion(WebDriver driver) {
		// Vamos a enviar peticion
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Enviamos peticion
		elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'Lucas')]/following-sibling::*/a[contains(@href, 'friend/add')]");
		elementos.get(0).click();
	}

}