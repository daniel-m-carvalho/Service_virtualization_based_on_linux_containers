package pt.isel.leic.svlc.util.helm;

import com.marcnuri.helm.*;
import com.marcnuri.helm.DependencyCommand.DependencySubcommand;
import pt.isel.leic.svlc.util.helm.configurations.*;

import static pt.isel.leic.svlc.util.executers.ExecIfElse.execIf;
import static pt.isel.leic.svlc.util.executers.ExecIfElse.execIfElse;

@Deprecated
public class HelmManagerUtil {

    /**
     * Configures the Helm install command.
     * @param command The InstallCommand object.
     * @param config The Helm install config.
     */
    public static void installConfig(InstallCommand command, HelmInstallConfig config) throws Exception {
        // Basic config
        execIfElse(config.isGenerateName(), command::generateName, () -> command.withName(config.getName()));
        execIf(config.isDescription(), () -> command.withDescription(config.getDescription()));

        //Namespace config
        execIf(config.isNameTemplate(), () -> command.withNameTemplate(config.getNameTemplate()));
        execIfElse(config.isCreateNamespace(), command::createNamespace, () -> command.withNamespace(config.getNamespace()));

        //Security config
        execIf(config.isKubeConfig(), () -> command.withKubeConfig(config.getKubeConfig()));
        execIf(config.isCertFile(), () -> command.withCertFile(config.getCertFile()));
        execIf(config.isKeyFile(), () -> command.withKeyFile(config.getKeyFile()));
        execIf(config.isCaFile(), () -> command.withCaFile(config.getCaFile()));
        execIf(config.isInsecureSkipTlsVerify(), command::insecureSkipTlsVerify);
        execIf(config.isPlainHttp(), command::plainHttp);

        // Advanced config
        execIf(config.isDevel(), command::devel);
        execIf(config.isDependencyUpdate(), command::dependencyUpdate);
        execIf(config.isDisableOpenApiValidation(), command::disableOpenApiValidation);
        execIf(
            config.isDryRun() && config.isDryRunOption(),
            () -> {
                command.dryRun();
                return command.withDryRunOption(config.getDryRunOption());
            }
        );
        execIf(config.isWaitReady(), command::waitReady);
        execIf(config.isDebug(), command::debug);
    }


    /**
     * Configures the Helm upgrade command.
     * @param command The UpgradeCommand object.
     * @param config The Helm upgrade config.
     */
    public static void upgradeConfig(UpgradeCommand command, HelmUpgradeConfig config) throws Exception {
        // Basic config
        execIf(config.isReleaseName(), () -> command.withName(config.getReleaseName()));
        execIf(config.isDescription(), () -> command.withDescription(config.getDescription()));

        // Namespace config
        execIfElse(config.isCreateNamespace(), command::createNamespace, () -> command.withNamespace(config.getNamespace()));

        // Security config
        execIf(config.isKubeConfig(), () -> command.withKubeConfig(config.getKubeConfig()));
        execIf(config.isCertFile(), () -> command.withCertFile(config.getCertFile()));
        execIf(config.isKeyFile(), () -> command.withKeyFile(config.getKeyFile()));
        execIf(config.isCaFile(), () -> command.withCaFile(config.getCaFile()));
        execIf(config.isInsecureSkipTlsVerify(), command::insecureSkipTlsVerify);
        execIf(config.isPlainHttp(), command::plainHttp);

        // Advanced config

        // Lifecycle config
        execIf(config.isInstall(), command::install);
        execIf(config.isForce(), command::force);
        execIf(config.isResetValues(), command::resetValues);
        execIf(config.isReuseValues(), command::reuseValues);
        execIf(config.isResetThenReuseValues(), command::resetThenReuseValues);

        // Execution control config
        execIf(config.isAtomic(), command::atomic);
        execIf(config.isCleanupOnFail(), command::cleanupOnFail);

        // Development config
        execIf(config.isDevel(), command::devel);
        execIf(config.isDependencyUpdate(), command::dependencyUpdate);
        execIf(config.isDisableOpenApiValidation(), command::disableOpenApiValidation);
        execIf(
            config.isDryRun() && config.isDryRunOption(),
            () -> {
                command.dryRun();
                return command.withDryRunOption(config.getDryRunOption());
            }
        );

        // Runtime config
        execIf(config.isWaitReady(), command::waitReady);
        execIf(config.isDebug(), command::debug);
    }

    /**
     * Configures the Helm uninstall command.
     * @param command The UninstallCommand object.
     * @param config The Helm uninstall config.
     */
    public static void uninstallConfig(UninstallCommand command, HelmUninstallConfig config) throws Exception {
        // Basic config
        execIf(config.isDryRun(), command::dryRun);
        execIf(config.isNoHooks(), command::noHooks);
        execIf(config.isIgnoreNotFound(), command::ignoreNotFound);
        execIf(config.isKeepHistory(), command::keepHistory);
        execIf(config.isCascade(), () -> command.withCascade(config.getCascade()));

        // Namespace config
        execIf(config.isNamespace(), () -> command.withNamespace(config.getNamespace()));

        // Security config
        execIf(config.isKubeConfig(), () -> command.withKubeConfig(config.getKubeConfig()));

        // Advanced config
        execIf(config.isDebug(), command::debug);
    }

    /**
     *  Configures the Helm list command.
     *  @param command The ListCommand object.
     *  @param config The Helm list config.
     */
    public static void listConfig(ListCommand command, HelmListConfig config) throws Exception {
        // Namespace config
        execIf(config.isNamespace(), () -> command.withNamespace(config.getNamespace()));

        // Security config
        execIf(config.isKubeConfig(), () -> command.withKubeConfig(config.getKubeConfig()));

        // List filters
        execIf(config.isAllNamespaces(), command::allNamespaces);
        execIf(config.isAll(), command::all);
        execIf(config.isDeployed(), command::deployed);
        execIf(config.isFailed(), command::failed);
        execIf(config.isPending(), command::pending);
        execIf(config.isSuperseded(), command::superseded);
        execIf(config.isUninstalled(), command::uninstalled);
        execIf(config.isUninstalling(), command::uninstalling);
    }

    /**
     *  Configures the Helm lint command
     *  @param command The LintCommand object.
     *  @param config The Helm lint config.
     */
    public static void lintConfig(LintCommand command, HelmLintConfig config) throws Exception {
        // Basic config
        execIf(config.isStrict(), command::strict);
        execIf(config.isQuiet(), command::quiet);
    }

    /**
     *  Configures the Helm test command
     *  @param command The TestCommand object.
     *  @param config The Helm test config.
     */
    public static void testConfig(TestCommand command, HelmTestConfig config) throws Exception {
        // Basic config
        execIf(config.isTimeout(), () -> command.withTimeout(config.getTimeout()));

        // Namespace config
        execIf(config.isNamespace(), () -> command.withNamespace(config.getNamespace()));

        // Security config
        execIf(config.isKubeConfig(), () -> command.withKubeConfig(config.getKubeConfig()));

        // Advanced config
        execIf(config.isDebug(), command::debug);
    }

    /**
     *  Configures the Helm dependency command
     *  @param command The DependencySubcommand object.
     *  @param config The Helm dependency config.
     */
    @Deprecated
    public static <T> void dependencyConfig(DependencySubcommand<T> command, HelmDependencyConfig config) throws Exception {
        // Basic config
        execIf(config.isKeyring(), () -> command.withKeyring(config.getKeyring()));
        execIf(config.isSkipRefresh(), command::skipRefresh);
        execIf(config.isVerify(), command::verify);

        // Advanced config
        execIf(config.isDebug(), command::debug);
    }

    /**
     *  Configures the Helm package command
     *  @param command The PackageCommand object.
     *  @param config The Helm package config.
     */
    public static void packageConfig(PackageCommand command, HelmPackageConfig config) throws Exception {
        // Basic config
        execIf(config.isDestination(), () -> command.withDestination(config.getDestination()));
        execIf(config.isSign(), () -> {
            command.sign();
            command.withKey(config.getKeyUID());
            return command.withKeyring(config.getKeyring());
        });
        execIf(config.isPassphraseFile(), () -> command.withPassphraseFile(config.getPassphraseFile()));
    }
}
