package pt.isel.leic.svlc.helm;

import com.marcnuri.helm.*;
import com.marcnuri.helm.DependencyCommand.DependencySubcommand;
import com. marcnuri. helm. DependencyListResult.Dependency;
import pt.isel.leic.svlc.util.helm.configurations.*;

import java.nio.file.Path;
import java.util.List;

import static pt.isel.leic.svlc.util.helm.HelmManagerUtil.*;

/**
 * Represents a class that handles the Helm API.
 * It provides methods to install, upgrade, uninstall, list,
 * show, lint, test, version, and package charts.
 */
@Deprecated
public class HelmManager {

    private  String chartName; // Name of the chart
    private String path;    // Path to the Helm chart or directory
    private final Helm helm; // Helm object

    /**
     * Constructs a HelmManager object with no parameters.
     */
    public HelmManager() {
        this.helm = new Helm(createPath(null));
    }

    /**
     *  Constructs a HelmManager object with a chart name.
     *  @param chartName The name of the chart.
     */
    public HelmManager(String chartName) {
        this.helm = new Helm(createPath(chartName));
    }

    /**
     * Constructs a HelmManager object with a Helm object.
     * @param helm The Helm object.
     * If null, a new Helm object is created.
     */
    public HelmManager(Helm helm) {
        this.helm = helm;
    }

    /**
     * Gets the name of the chart.
     * @return The name of the chart.
     */
    public String getChartName() {
        return chartName;
    }

    /**
     * Gets the path to the Helm chart.
     * @return The path to the Helm chart or directory.
     */
    public String getPath() {
        return path;
    }

    /**
     * Creates a Path object with the path to the Helm chart.
     * @param chartName The name of the chart.
     * @return The Path object.
     */
    private Path createPath(String chartName) {
        this.chartName = chartName;
        String helmPath = System.getenv("HELM_PATH");
        path = chartName != null ? helmPath + "/" + chartName : helmPath;
        return Path.of(path);
    }

    /**
     * Creates a chart.
     * @param chartName The name of the chart.
     * @return The path to the chart.
     */
    public String createChart(String chartName) {
        Helm.create().withName(chartName).withDir(Path.of(path)).call();
        return path + "/" + chartName;
    }

    /**
     * Installs a chart.
     * @param config The config to install the chart.
     * @return The output of the installation.
     */
    public String installChart(HelmInstallConfig config) throws Exception {
        InstallCommand command = config.isChartReference() ?
                Helm.install(config.getChartReference()) : helm.install();

        installConfig(command, config);
        config.getValues().forEach(command::set);
        return command.call().getOutput();
    }

    /**
     * Upgrades a chart.
     * @param config The config to upgrade the chart.
     * @return The output of the upgrade.
     */
    public String upgradeChart(HelmUpgradeConfig config) throws Exception {
        UpgradeCommand command = config.isChartReference() ?
                Helm.upgrade(config.getChartReference()) : helm.upgrade();

        upgradeConfig(command, config);
        config.getValues().forEach(command::set);
        return command.call().getOutput();
    }

    /**
     * Uninstalls a chart.
     * @param config The config to uninstall the chart.
     * @return The output of the uninstallation.
     */
    public String uninstallChart(HelmUninstallConfig config) throws Exception {
        UninstallCommand command = Helm.uninstall(config.getChartReference());
        uninstallConfig(command, config);
        return command.call();
    }

    /**
     * Lists all the releases.
     * @param config The config to list the releases.
     * @return The output of the list.
     */
    public List<String> listReleases(HelmListConfig config) throws Exception {
        ListCommand command = Helm.list();
        listConfig(command, config);
        return command.call().stream().map(Release::getOutput).toList();
    }

    /**
     * Lints a chart.
     * @param config The config to lint the chart.
     * @return The messages of the lint.
     * @throws RuntimeException If the lint fails.
     */
    public List<String> lintChart(HelmLintConfig config) throws Exception {
        LintCommand lintCommand = helm.lint();
        lintConfig(lintCommand, config);
        LintResult lintResult = lintCommand.call();

        if (lintResult.isFailed()) {
            throw new RuntimeException(lintResult.getMessages().toString());
        }

        return lintResult.getMessages();
    }

    /**
     * Tests a chart.
     * @param config The config to test the chart.
     * @return The output of the test.
     */
    public String testChart(HelmTestConfig config) throws Exception {
        TestCommand testCommand = Helm.test(config.getChartReference());
        testConfig(testCommand, config);
        return testCommand.call().getOutput();
    }

    /**
     * Shows the version of Helm.
     * @return The version of Helm.
     */
    public String version() {
        return Helm.version().call();
    }

    /**
     * Shows the dependencies of a chart.
     * @param config The config to show the dependencies.
     * @return The output of the show.
     */
    public String dependencyBuild(HelmDependencyConfig config) throws Exception {
        DependencySubcommand<String> command = helm.dependency().build();
        dependencyConfig(command, config);
        return command.call();
    }

    /**
     * Shows the dependencies of a chart.
     * @param config The config to show the dependencies.
     * @return The list of dependencies.
     */
    public List<String> dependencyList(HelmDependencyConfig config) throws Exception {
        DependencySubcommand<DependencyListResult> command = helm.dependency().list();
        dependencyConfig(command, config);
        return command.call().getDependencies().stream().map(Dependency::getName).toList();
    }

    /**
     * Updates the dependencies of a chart.
     * @return The output of the update.
     */
    public String dependencyUpdate(HelmDependencyConfig config) throws Exception {
        DependencySubcommand<String> command = helm.dependency().update();
        dependencyConfig(command, config);
        return command.call();
    }

    /**
     * Packages a chart.
     * @param config The config to package the chart.
     * @return The Helm package object that can be used to simply install, upgrade, ...
     * Or create a new HelmManager object with the Helm object to make a more complex command.
     */
    public Helm packageChart(HelmPackageConfig config) throws Exception {
        PackageCommand command = helm.packageIt();
        packageConfig(command, config);
        return command.call();
    }
}
