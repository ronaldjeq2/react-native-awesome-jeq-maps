import { NativeModules, requireNativeComponent } from 'react-native';
import type { AwesomeJeqMapsViewProps, MarkerClickEvent } from './types'; // Importar los tipos con `import type`

const AwesomeJeqMapsView = requireNativeComponent<AwesomeJeqMapsViewProps>('AwesomeJeqMapsView');

const { AwesomeJeqMaps } = NativeModules;

export type { AwesomeJeqMapsViewProps, MarkerClickEvent };
export { AwesomeJeqMaps, AwesomeJeqMapsView };
