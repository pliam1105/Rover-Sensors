﻿#pragma checksum "C:\PliamOnedrive\OneDrive - HATZ GROUP\Pliam\Desktop\RoverSensorsWindows\RoverSensors\RoverSensors\MainPage.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "6DAB44662536C5DB60862609AFA31844"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace RoverSensors
{
    partial class MainPage : 
        global::Windows.UI.Xaml.Controls.Page, 
        global::Windows.UI.Xaml.Markup.IComponentConnector,
        global::Windows.UI.Xaml.Markup.IComponentConnector2
    {
        /// <summary>
        /// Connect()
        /// </summary>
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 10.0.18362.1")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public void Connect(int connectionId, object target)
        {
            switch(connectionId)
            {
            case 2: // MainPage.xaml line 13
                {
                    this.NavigationView = (global::Microsoft.UI.Xaml.Controls.NavigationView)(target);
                    ((global::Microsoft.UI.Xaml.Controls.NavigationView)this.NavigationView).ItemInvoked += this.NavigationView_ItemInvoked;
                    ((global::Microsoft.UI.Xaml.Controls.NavigationView)this.NavigationView).SelectionChanged += this.NavigationView_SelectionChanged;
                    ((global::Microsoft.UI.Xaml.Controls.NavigationView)this.NavigationView).Loaded += this.NavigationView_Loaded;
                }
                break;
            case 3: // MainPage.xaml line 60
                {
                    this.WebCommands = (global::Windows.UI.Xaml.Controls.CommandBar)(target);
                }
                break;
            case 4: // MainPage.xaml line 66
                {
                    this.ContentFrame = (global::Windows.UI.Xaml.Controls.Frame)(target);
                    ((global::Windows.UI.Xaml.Controls.Frame)this.ContentFrame).NavigationFailed += this.ContentFrame_NavigationFailed;
                }
                break;
            case 5: // MainPage.xaml line 61
                {
                    this.web_but = (global::Windows.UI.Xaml.Controls.AppBarButton)(target);
                    ((global::Windows.UI.Xaml.Controls.AppBarButton)this.web_but).Click += this.Web_but_Click;
                }
                break;
            case 6: // MainPage.xaml line 62
                {
                    this.contact_but = (global::Windows.UI.Xaml.Controls.AppBarButton)(target);
                    ((global::Windows.UI.Xaml.Controls.AppBarButton)this.contact_but).Click += this.Contact_but_Click;
                }
                break;
            case 7: // MainPage.xaml line 63
                {
                    this.question_but = (global::Windows.UI.Xaml.Controls.AppBarButton)(target);
                    ((global::Windows.UI.Xaml.Controls.AppBarButton)this.question_but).Click += this.Question_but_Click;
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
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 10.0.18362.1")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public global::Windows.UI.Xaml.Markup.IComponentConnector GetBindingConnector(int connectionId, object target)
        {
            global::Windows.UI.Xaml.Markup.IComponentConnector returnValue = null;
            return returnValue;
        }
    }
}

