#pragma checksum "C:\Users\pliam\OneDrive\Desktop\FLL 2019 ROVER\RoverSensorsWindows\RoverSensors\RoverSensors\Views\rover_route_small.xaml" "{8829d00f-11b8-4213-878b-770e8597ac16}" "295DF28A5ECA382C9FF9C9431A7CAC108CC0D17B7A4927D11D86B6AD8CEA5920"
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
    partial class rover_route_small : 
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
            case 2: // Views\rover_route_small.xaml line 12
                {
                    this.RouteGrid = (global::Windows.UI.Xaml.Controls.Grid)(target);
                    ((global::Windows.UI.Xaml.Controls.Grid)this.RouteGrid).SizeChanged += this.RouteGrid_SizeChanged;
                }
                break;
            case 3: // Views\rover_route_small.xaml line 13
                {
                    this.RoutePopup = (global::Windows.UI.Xaml.Controls.Primitives.Popup)(target);
                }
                break;
            case 4: // Views\rover_route_small.xaml line 26
                {
                    this.routeMap = (global::Windows.UI.Xaml.Controls.Maps.MapControl)(target);
                }
                break;
            case 5: // Views\rover_route_small.xaml line 28
                {
                    this.route_refresh = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.route_refresh).Click += this.Route_refresh_Click;
                }
                break;
            case 6: // Views\rover_route_small.xaml line 14
                {
                    this.RoutePopupGrid = (global::Windows.UI.Xaml.Controls.Grid)(target);
                }
                break;
            case 7: // Views\rover_route_small.xaml line 15
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

