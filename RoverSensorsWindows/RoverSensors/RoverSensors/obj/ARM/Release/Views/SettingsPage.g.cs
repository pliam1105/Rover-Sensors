﻿#pragma checksum "C:\Users\pliam\OneDrive\Desktop\FLL 2019 ROVER\RoverSensorsWindows\RoverSensors\RoverSensors\Views\SettingsPage.xaml" "{8829d00f-11b8-4213-878b-770e8597ac16}" "41C96540B9C844C7F04DAA768419D003B4F25CA1522B99E6CED6B1D362833D97"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace RoverSensors.Views
{
    partial class SettingsPage : 
        global::Windows.UI.Xaml.Controls.Page, 
        global::Windows.UI.Xaml.Markup.IComponentConnector,
        global::Windows.UI.Xaml.Markup.IComponentConnector2
    {
        /// <summary>
        /// Connect()
        /// </summary>
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 0.0.0.0")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public void Connect(int connectionId, object target)
        {
            switch(connectionId)
            {
            case 2: // Views\SettingsPage.xaml line 15
                {
                    this.TopPanel = (global::Windows.UI.Xaml.Controls.StackPanel)(target);
                    ((global::Windows.UI.Xaml.Controls.StackPanel)this.TopPanel).SizeChanged += this.StackPanel_SizeChanged;
                }
                break;
            case 3: // Views\SettingsPage.xaml line 31
                {
                    this.DoneButton = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.DoneButton).Click += this.DoneButton_Click;
                }
                break;
            case 4: // Views\SettingsPage.xaml line 25
                {
                    this.PrefixBox = (global::Windows.UI.Xaml.Controls.ComboBox)(target);
                    ((global::Windows.UI.Xaml.Controls.ComboBox)this.PrefixBox).SelectionChanged += this.PrefixBox_SelectionChanged;
                }
                break;
            case 5: // Views\SettingsPage.xaml line 29
                {
                    this.UrlBox = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                    ((global::Windows.UI.Xaml.Controls.TextBox)this.UrlBox).TextChanged += this.UrlBox_TextChanged;
                }
                break;
            default:
                break;
            }
            this._contentLoaded = true;
        }

        /// <summary>
        /// GetBindingConnector(int connectionId, object target)
        /// </summary>
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 0.0.0.0")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public global::Windows.UI.Xaml.Markup.IComponentConnector GetBindingConnector(int connectionId, object target)
        {
            global::Windows.UI.Xaml.Markup.IComponentConnector returnValue = null;
            return returnValue;
        }
    }
}

