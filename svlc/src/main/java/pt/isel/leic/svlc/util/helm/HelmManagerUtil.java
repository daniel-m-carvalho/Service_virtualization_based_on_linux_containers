package pt.isel.leic.svlc.util.helm;

//import com.marcnuri.helm.*;
//import com.marcnuri.helm.DependencyCommand.DependencySubcommand;
import pt.isel.leic.svlc.util.helm.configurations.*;

//public class HelmManagerUtil {

//    /**
//     * Configures the Helm install command.
//     * @param command The InstallCommand object.
//     * @param config The Helm install config.
//     */
//    public static void installConfig(InstallCommand command, HelmInstallConfig config) {
//        // Basic config
//        applyConfigOption(config.isGenerateName(), command::generateName, () -> command.withName(config.getName()));
//        applyConfigOption(config.isDescription(), () -> command.withDescription(config.getDescription()));
//
//        //Namespace config
//        applyConfigOption(config.isNameTemplate(), () -> command.withNameTemplate(config.getNameTemplate()));
//        applyConfigOption(config.isCreateNamespace(), command::createNamespace, () -> command.withNamespace(config.getNamespace()));
//
//        //Security config
//        applyConfigOption(config.isKubeConfig(), () -> command.withKubeConfig(config.getKubeConfig()));
//        applyConfigOption(config.isCertFile(), () -> command.withCertFile(config.getCertFile()));
//        applyConfigOption(config.isKeyFile(), () -> command.withKeyFile(config.getKeyFile()));
//        applyConfigOption(config.isCaFile(), () -> command.withCaFile(config.getCaFile()));
//        applyConfigOption(config.isInsecureSkipTlsVerify(), command::insecureSkipTlsVerify);
//        applyConfigOption(config.isPlainHttp(), command::plainHttp);
//
//        // Advanced config
//        applyConfigOption(config.isDevel(), command::devel);
//        applyConfigOption(config.isDependencyUpdate(), command::dependencyUpdate);
//        applyConfigOption(config.isDisableOpenApiValidation(), command::disableOpenApiValidation);
//        applyConfigOption(
//                config.isDryRun() && config.isDryRunOption(),
//                () -> {
//                    command.dryRun();
//                    command.withDryRunOption(config.getDryRunOption());
//                }
//        );
//        applyConfigOption(config.isWaitReady(), command::waitReady);
//        applyConfigOption(config.isDebug(), command::debug);
//    }
//
//
//    /**
//     * Configures the Helm upgrade command.
//     * @param command The UpgradeCommand object.
//     * @param config The Helm upgrade config.
//     */
//    public static void upgradeConfig(UpgradeCommand command, HelmUpgradeConfig config) {
//        // Basic config
//        applyConfigOption(config.isReleaseName(), () -> command.withName(config.getReleaseName()));
//        applyConfigOption(config.isDescription(), () -> command.withDescription(config.getDescription()));
//
//        // Namespace config
//        applyConfigOption(config.isCreateNamespace(), command::createNamespace, () -> command.withNamespace(config.getNamespace()));
//
//        // Security config
//        applyConfigOption(config.isKubeConfig(), () -> command.withKubeConfig(config.getKubeConfig()));
//        applyConfigOption(config.isCertFile(), () -> command.withCertFile(config.getCertFile()));
//        applyConfigOption(config.isKeyFile(), () -> command.withKeyFile(config.getKeyFile()));
//        applyConfigOption(config.isCaFile(), () -> command.withCaFile(config.getCaFile()));
//        applyConfigOption(config.isInsecureSkipTlsVerify(), command::insecureSkipTlsVerify);
//        applyConfigOption(config.isPlainHttp(), command::plainHttp);
//
//        // Advanced config
//
//        // Lifecycle config
//        applyConfigOption(config.isInstall(), command::install);
//        applyConfigOption(config.isForce(), command::force);
//        applyConfigOption(config.isResetValues(), command::resetValues);
//        applyConfigOption(config.isReuseValues(), command::reuseValues);
//        applyConfigOption(config.isResetThenReuseValues(), command::resetThenReuseValues);
//
//        // Execution control config
//        applyConfigOption(config.isAtomic(), command::atomic);
//        applyConfigOption(config.isCleanupOnFail(), command::cleanupOnFail);
//
//        // Development config
//        applyConfigOption(config.isDevel(), command::devel);
//        applyConfigOption(config.isDependencyUpdate(), command::dependencyUpdate);
//        applyConfigOption(config.isDisableOpenApiValidation(), command::disableOpenApiValidation);
//        applyConfigOption(
//                config.isDryRun() && config.isDryRunOption(),
//                () -> {
//                    command.dryRun();
//                    command.withDryRunOption(config.getDryRunOption());
//                }
//        );
//
//        // Runtime config
//        applyConfigOption(config.isWaitReady(), command::waitReady);
//        applyConfigOption(config.isDebug(), command::debug);
//    }
//
//    /**
//     * Configures the Helm uninstall command.
//     * @param command The UninstallCommand object.
//     * @param config The Helm uninstall config.
//     */
//    public static void uninstallConfig(UninstallCommand command, HelmUninstallConfig config) {
//        // Basic config
//        applyConfigOption(config.isDryRun(), command::dryRun);
//        applyConfigOption(config.isNoHooks(), command::noHooks);
//        applyConfigOption(config.isIgnoreNotFound(), command::ignoreNotFound);
//        applyConfigOption(config.isKeepHistory(), command::keepHistory);
//        applyConfigOption(config.isCascade(), () -> command.withCascade(config.getCascade()));
//
//        // Namespace config
//        applyConfigOption(config.isNamespace(), () -> command.withNamespace(config.getNamespace()));
//
//        // Security config
//        applyConfigOption(config.isKubeConfig(), () -> command.withKubeConfig(config.getKubeConfig()));
//
//        // Advanced config
//        applyConfigOption(config.isDebug(), command::debug);
//    }
//
//    /**
//     *  Configures the Helm list command.
//     *  @param command The ListCommand object.
//     *  @param config The Helm list config.
//     */
//    public static void listConfig(ListCommand command, HelmListConfig config) {
//        // Namespace config
//        applyConfigOption(config.isNamespace(), () -> command.withNamespace(config.getNamespace()));
//
//        // Security config
//        applyConfigOption(config.isKubeConfig(), () -> command.withKubeConfig(config.getKubeConfig()));
//
//        // List filters
//        applyConfigOption(config.isAllNamespaces(), command::allNamespaces);
//        applyConfigOption(config.isAll(), command::all);
//        applyConfigOption(config.isDeployed(), command::deployed);
//        applyConfigOption(config.isFailed(), command::failed);
//        applyConfigOption(config.isPending(), command::pending);
//        applyConfigOption(config.isSuperseded(), command::superseded);
//        applyConfigOption(config.isUninstalled(), command::uninstalled);
//        applyConfigOption(config.isUninstalling(), command::uninstalling);
//    }
//
//    /**
//     *  Configures the Helm lint command
//     *  @param command The LintCommand object.
//     *  @param config The Helm lint config.
//     */
//    public static void lintConfig(LintCommand command, HelmLintConfig config) {
//        // Basic config
//        applyConfigOption(config.isStrict(), command::strict);
//        applyConfigOption(config.isQuiet(), command::quiet);
//    }
//
//    /**
//     *  Configures the Helm test command
//     *  @param command The TestCommand object.
//     *  @param config The Helm test config.
//     */
//    public static void testConfig(TestCommand command, HelmTestConfig config) {
//        // Basic config
//        applyConfigOption(config.isTimeout(), () -> command.withTimeout(config.getTimeout()));
//
//        // Namespace config
//        applyConfigOption(config.isNamespace(), () -> command.withNamespace(config.getNamespace()));
//
//        // Security config
//        applyConfigOption(config.isKubeConfig(), () -> command.withKubeConfig(config.getKubeConfig()));
//
//        // Advanced config
//        applyConfigOption(config.isDebug(), command::debug);
//    }
//
//    /**
//     *  Configures the Helm dependency command
//     *  @param command The DependencySubcommand object.
//     *  @param config The Helm dependency config.
//     */
//    public static <T> void dependencyConfig(DependencySubcommand<T> command, HelmDependencyConfig config) {
//        // Basic config
//        applyConfigOption(config.isKeyring(), () -> command.withKeyring(config.getKeyring()));
//        applyConfigOption(config.isSkipRefresh(), command::skipRefresh);
//        applyConfigOption(config.isVerify(), command::verify);
//
//        // Advanced config
//        applyConfigOption(config.isDebug(), command::debug);
//    }
//
//    /**
//     *  Configures the Helm package command
//     *  @param command The PackageCommand object.
//     *  @param config The Helm package config.
//     */
//    public static void packageConfig(PackageCommand command, HelmPackageConfig config) {
//        // Basic config
//        applyConfigOption(config.isDestination(), () -> command.withDestination(config.getDestination()));
//        applyConfigOption(config.isSign(), () -> {
//            command.sign();
//            command.withKey(config.getKeyUID());
//            command.withKeyring(config.getKeyring());
//        });
//        applyConfigOption(config.isPassphraseFile(), () -> command.withPassphraseFile(config.getPassphraseFile()));
//    }
//}
