package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.theme.token.MyAliasTokens
import com.example.theme.token.MyAppBarToken
import com.example.theme.token.MyButtonTokens
import com.example.theme.token.MyFABToken
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.FloatingActionButton
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding

class V2ButtonsActivity : DemoActivity() {
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        val v2ActivityComposeBinding = V2ActivityComposeBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )
        v2ActivityComposeBinding.composeHere.setContent {
            val controlTokens = ControlTokens()
            var fabState by rememberSaveable { mutableStateOf(FABState.Expanded) }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                FluentTheme {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        BasicText(
                            "Button to update Theme via Global & Alias token",
                            style = TextStyle(
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode
                                )
                            )
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterHorizontally
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                style = ButtonStyle.OutlinedButton,
                                size = ButtonSize.Medium,
                                onClick = {
                                    FluentTheme.updateAliasTokens(AliasTokens())
                                    FluentTheme.updateControlTokens(
                                        controlTokens.updateToken(
                                            ControlTokens.ControlType.Button,
                                            ButtonTokens()
                                        )
                                    )
                                },
                                text = "Theme 1"
                            )

                            Button(
                                style = ButtonStyle.OutlinedButton,
                                size = ButtonSize.Medium,
                                onClick = {
                                    FluentTheme.updateAliasTokens(MyAliasTokens())
                                    FluentTheme.updateControlTokens(
                                        controlTokens.updateToken(
                                            ControlTokens.ControlType.Button,
                                            MyButtonTokens()
                                        )
                                    )
                                },
                                text = "Theme 2"
                            )

                            Button(
                                style = ButtonStyle.OutlinedButton,
                                size = ButtonSize.Medium,
                                onClick = {
                                    FluentTheme.updateControlTokens(
                                        controlTokens.updateToken(
                                            ControlTokens.ControlType.AppBar,
                                            MyAppBarToken()
                                        ).updateToken(
                                            ControlTokens.ControlType.FloatingActionButton,
                                            MyFABToken()
                                        )
                                    )
                                },
                                text = "Theme 3"
                            )
                        }
                    }
                }

                Divider()

                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        BasicText(
                            "Activity level customization with Auto theme",
                            style = TextStyle(
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode
                                )
                            )
                        )

                        // TODO Investigate better ways to save activity Theme state
                        // TODO One possible way is to use State Holders
                        var aliasTokens by rememberSaveable { mutableStateOf(AliasTokens()) }

                        FluentTheme(aliasTokens = aliasTokens, controlTokens = ControlTokens()) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    5.dp,
                                    Alignment.CenterVertically
                                )
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        5.dp,
                                        Alignment.CenterHorizontally
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button(
                                        onClick = { aliasTokens = AliasTokens() },
                                        text = "Theme1"
                                    )
                                    Button(
                                        onClick = { aliasTokens = MyAliasTokens() },
                                        text = "Theme2"
                                    )
                                }
                                CreateButtons()
                            }
                        }
                    }

                    item {
                        FluentTheme {
                            BasicText(
                                "Button with selected theme, auto mode and overridden control token",
                                style = TextStyle(
                                    color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                        themeMode
                                    )
                                )
                            )
                            CreateButtons(MyButtonTokens())
                        }
                    }
                }
            }
            FluentTheme {
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .fillMaxSize()
                        .focusable(false)
                ) {
                    val fabText = "FAB Text"
                    FloatingActionButton(
                        size = FABSize.Small,
                        state = fabState,
                        onClick = {
                            val toastText: String
                            if (fabState == FABState.Expanded) {
                                toastText = "FAB Collapsed"
                                fabState = FABState.Collapsed
                            } else {
                                toastText = "FAB Expanded"
                                fabState = FABState.Expanded
                            }
                            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                        },
                        icon = Icons.Filled.Email,
                        modifier = Modifier.padding(16.dp),
                        text = fabText,
                    )
                }
            }
        }
    }

    @Composable
    fun icon(enabled: Boolean): ImageVector {
        return if (enabled)
            Icons.Outlined.Favorite
        else
            Icons.Outlined.ThumbUp
    }

    @Composable
    fun CreateButtons(buttonToken: ButtonTokens? = null) {
        var enabled by rememberSaveable { mutableStateOf(true) }
        Column {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    style = ButtonStyle.Button,
                    size = ButtonSize.Large,
                    buttonTokens = buttonToken,
                    onClick = { enabled = !enabled },
                    text = if (enabled) "Click to Disable" else "Click to Enable"
                )
            }

            Spacer(Modifier.height(20.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize(1.0F),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var clicks by rememberSaveable { mutableStateOf(0) }
                val onClickLambda: () -> Unit = { clicks++ }
                val text = "Button $clicks"
                val toggleIcon = clicks % 2 == 0

                Button(
                    style = if (clicks < 3) ButtonStyle.Button else ButtonStyle.TextButton,
                    size = if (clicks < 3) ButtonSize.Large else ButtonSize.Small,
                    enabled = enabled,
                    buttonTokens = if (clicks < 3) buttonToken else ButtonTokens(),
                    onClick = onClickLambda,
                    icon = icon(toggleIcon),
                    text = text
                )

                Button(
                    style = ButtonStyle.Button,
                    size = ButtonSize.Medium,
                    enabled = enabled,
                    buttonTokens = buttonToken,
                    onClick = onClickLambda,
                    icon = icon(toggleIcon),
                    text = "Long text displayed on this button. This Is long text."
                )

                Button(
                    style = ButtonStyle.OutlinedButton,
                    size = ButtonSize.Small,
                    enabled = enabled,
                    buttonTokens = buttonToken,
                    onClick = onClickLambda,
                    icon = icon(toggleIcon),
                    text = "Outlined $text"
                )
                Button(
                    style = ButtonStyle.TextButton,
                    size = ButtonSize.Small,
                    enabled = enabled,
                    buttonTokens = buttonToken,
                    onClick = onClickLambda,
                    icon = icon(clicks % 2 == 0),
                    text = "Text $text"
                )
            }
        }
    }
}
