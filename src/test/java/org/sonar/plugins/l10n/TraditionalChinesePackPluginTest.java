package org.sonar.plugins.l10n;

import org.junit.Test;
import org.junit.Assert;
import org.sonar.api.internal.PluginContextImpl;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.Plugin;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.utils.Version;
import org.sonar.test.i18n.I18nMatchers;


public class TraditionalChinesePackPluginTest {
    @Test
    public void bundles_should_be_up_to_date() {
        // Skip test, because of merge conflict in SonarQube-6.1
        // https://github.com/SonarSource/sonarqube/commit/50c03de3431007269b0966a8fdf1fe032c9521f6
        I18nMatchers.assertBundlesUpToDate();
    }

    // coverage
    @Test
    public void testChinesePackPlugin() {
        TraditionalChinesePackPlugin traditionalChinesePackPlugin = new TraditionalChinesePackPlugin();

        String pluginName = traditionalChinesePackPlugin.toString();
        Assert.assertEquals("TraditionalChinesePackPlugin", pluginName);

        SonarRuntime runtime = SonarRuntimeImpl.forSonarQube(Version.create(8, 5),
                SonarQubeSide.SCANNER, SonarEdition.COMMUNITY);
        Plugin.Context context = new PluginContextImpl.Builder().setSonarRuntime(runtime).build();
        traditionalChinesePackPlugin.define(context);
    }
}
