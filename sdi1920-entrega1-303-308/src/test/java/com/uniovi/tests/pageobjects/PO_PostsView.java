package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.util.UtilMethods;

public class PO_PostsView extends PO_NavView {

	static public void fillForm(WebDriver driver, String titlep, String textp) {

		WebElement title = driver.findElement(By.name("title"));
		title.click();
		title.clear();
		title.sendKeys(titlep);

		WebElement text = driver.findElement(By.name("text"));
		text.click();
		text.clear();
		text.sendKeys(textp);

		// Pulsar el boton de Alta.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
	
	static public void fillForm(WebDriver driver, String titlep, String textp, String photoUrl) {
		WebElement photo = driver.findElement(By.name("file"));
		photo.sendKeys(UtilMethods.getAbsolutePath(photoUrl));

		fillForm(driver, titlep, textp);
	}
}
