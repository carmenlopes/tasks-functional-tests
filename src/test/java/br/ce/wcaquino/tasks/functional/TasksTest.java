package br.ce.wcaquino.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {

	@BeforeClass
	public static void setup() {
//		System.setProperty("webdriver.chrome.driver", "C:\\dev\\seleniumdriver\\chromedriver.exe");

	}

	public static WebDriver acessarAplicacao() throws MalformedURLException {

//		WebDriver driver = new ChromeDriver();
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.73:4444/wd/hub"), cap);
		driver.navigate().to("http://192.168.1.73:8001/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;

	}

	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {

			// clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();

			// escrever descricao
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

			// escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/03/2022");

			// clicar em salvar
			driver.findElement(By.id("saveButton")).click();

			// validar msg de sucesso
			String message = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Sucess!", message);

		} finally {
			// fechar o browser
			driver.quit();
		}
	}

	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();

		try {
			// clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();

			// escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/03/2022");

			// clicar em salvar
			driver.findElement(By.id("saveButton")).click();

			// validar msg de sucesso
			String message = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("preencha a descrição da tarefa", message);

		} finally {
			// fechar o browser
			driver.quit();
		}

	}

	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {

			// clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();

			// escrever descricao
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

			// clicar em salvar
			driver.findElement(By.id("saveButton")).click();

			// validar msg de sucesso
			String message = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Preencha a data da task", message);

		} finally {
			// fechar o browser
			driver.quit();
		}
	}

	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {

			// clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();

			// escrever descricao
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

			// escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/03/2020");

			// clicar em salvar
			driver.findElement(By.id("saveButton")).click();

			// validar msg de erro
			String message = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Preencha a data da task com data futura", message);

		} finally {
			// fechar o browser
			driver.quit();
		}
	}
}
