﻿#pragma checksum "C:\Users\pliam\OneDrive\Desktop\FLL 2019 ROVER\RoverSensorsWindows\RoverSensors\RoverSensors\Views\rover_route.xaml" "{8829d00f-11b8-4213-878b-770e8597ac16}" "56343DEDC99F3AB6F5781D284D097F2D4EF879724954579E2A96129965958E11"
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
    partial class rover_route : 
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
            case 2: // Views\rover_route.xaml line 12
                {
                    this.RouteGrid = (global::Windows.UI.Xaml.Controls.Grid)(target);
                    ((global::Windows.UI.Xaml.Controls.Grid)this.RouteGrid).SizeChanged += this.RouteGrid_SizeChanged;
                }
                break;
            case 3: // Views\rover_route.xaml line 13
                {
                    this.RoutePopup = (global::Windows.UI.Xaml.Controls.Primitives.Popup)(target);
                }
                break;
            case 4: // Views\rover_route.xaml line 26
                {
                    this.routeMap = (global::Windows.UI.Xaml.Controls.Maps.MapControl)(target);
                }
                break;
            case 5: // Views\rover_route.xaml line 28
                {
                    this.route_refresh = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.route_refresh).Click += this.Route_refresh_Click;
                }
                break;
            case 6: // Views\rover_route.xaml line 14
                {
                    this.RoutePopupGrid = (global::Windows.UI.Xaml.Controls.Grid)(target);
                }
                break;
            case 7: // Views\rover_route.xaml line 15
                {
                    this.RoutePopupBorder = (global::Windows.UI.Xaml.Controls.Border)(target);
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
