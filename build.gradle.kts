// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlinAndroid) apply false
  alias(libs.plugins.spotless)
}

spotless {
  kotlin {
    target("**/*.kt", "**/*.kts")
    ktlint().editorConfigOverride(
      mapOf(
        "indent_size" to "2",
        "disabled_rules" to "package-name",
        "ktlint_standard_function-naming" to "disabled",
        "ktlint_standard_filename" to "disabled",
        "ij_kotlin_allow_trailing_comma" to "true",
        "ij_kotlin_allow_trailing_comma_on_call_site" to "true",
      ),
    )
    trimTrailingWhitespace()
    endWithNewline()
  }
}
